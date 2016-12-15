package com.simon.app;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import com.simon.utils.IPop;
import com.simon.utils.IPop.PosBtnCallBack;
import com.simon.utils.IToast;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;

public class ActHome extends ActBase implements OnClickListener {

	private static final String TAG = "ActHome";

	private Button btnUp, btnDown, btnLeft, btnRight, btnStop;
	private Button btnRest, btnSet;
	private Button btnX, btnY, btnA, btnB;

	// 蓝牙相关变量
	private final static int REQUEST_CONNECT_DEVICE = 1; // 宏定义查询设备句柄
	private final static String MY_UUID = "00001101-0000-1000-8000-00805F9B34FB"; // SPP服务UUID号
	private BluetoothAdapter _bluetooth = BluetoothAdapter.getDefaultAdapter(); // 获取本地蓝牙适配器
	private BluetoothDevice _device = null; // 蓝牙设备
	private BluetoothSocket _socket = null; // 蓝牙通信socket
	private InputStream is; // 输入流，用来接收蓝牙数据 //暂时不用
	boolean bThread = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.initStart();
	}

	// 默认开始
	private void initStart() {
		// 打开界面检测本地蓝牙是否打开，如果打开本地蓝牙设备不成功，提示信息，结束程序
		if (_bluetooth == null) {
			IToast.hint(this, "打开本地蓝牙失败，确定手机是否有蓝牙功能");
			finish();
			return;
		}

		// 设置设备可以被搜索
		new Thread() {
			@Override
			public void run() {
				if (_bluetooth.isEnabled() == false)
					_bluetooth.enable();
			}
		}.start();
	}

	// 连接 蓝牙按钮 包含 搜索蓝牙
	private void connectBluetooth() {
		if (_bluetooth.isEnabled() == false) { // 如果蓝牙服务不可用则提示
			IToast.hint(this, "打开蓝牙中...");
			return;
		}

		// 如果是未连接设备, 则打开DeviceListActivity进行设备搜索
		if (_socket == null) {
			Intent searchDeviceListActIntent = new Intent(this,ActDeviceList.class); // 跳转到搜索蓝牙窗口
			startActivityForResult(searchDeviceListActIntent,REQUEST_CONNECT_DEVICE); // 设置返回宏定义
			
		//如果是连接状态,APP界面的 set按钮 提示文字为 断开 .此时点击是断开连接功能了哦
		} else {
			// 关闭连接socket
			try {
				is.close();
				_socket.close();
				_socket = null;
				btnSet.setText("SET");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return;
	}

	// 接收来自选择蓝牙设备界面的结果，对应自startActivityForResult() 跳转的界面
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
			case REQUEST_CONNECT_DEVICE: // 连接结果，由DeviceListActivity设置返回
				// 响应返回结果
				if (resultCode == Activity.RESULT_OK) { // 连接成功，由DeviceListActivity设置返回
					// MAC地址，由DeviceListActivity设置返回
					String address = data.getExtras().getString(ActDeviceList.EXTRA_DEVICE_ADDRESS);
					// 得到蓝牙设备句柄
					_device = _bluetooth.getRemoteDevice(address);
	
					// 用服务号得到socket
					try {
						_socket = _device.createRfcommSocketToServiceRecord(UUID.fromString(MY_UUID));
					} catch (IOException e) {
						IToast.hint(this, "连接失败！");
					}
	
					// 连接socket
					try {
						_socket.connect();
						IToast.hint(this, "连接" + _device.getName() + "成功！");
						btnSet.setText("断开");
						
					} catch (IOException e) {
						//如果连接失败，全部清空相关句柄
						try {
							IToast.hint(this, "连接失败！");
							_socket.close();
							_socket = null;
						} catch (IOException ee) {
							IToast.hint(this, "连接失败！");
						}
						return;
					}
	
					// 打开socket输入流
					try {
						is = _socket.getInputStream(); // 得到蓝牙数据输入流
					} catch (IOException e) {
						IToast.hint(this, "接收数据失败！");
						return;
					}
					
					// 开启一个接收线程，实时读取 来自 蓝牙硬件发送过来的数据 （暂时不研发）
//					if(bThread==false){
//            			ReadThread.start();
//            			bThread=true;
//            		}else{
//            			bRun = true;
//            		}
	
				}
				break;
				
			default:
				break;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (_socket != null) {
			try {
				_socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// _bluetooth.disable();
	}

	// 发送信息到蓝牙设备
	private void send(String commandStr) {
		int i = 0;
		int n = 0;
		// 获取蓝牙连接输出流
		try {
			OutputStream os = _socket.getOutputStream(); 
			byte[] bos = commandStr.getBytes();
			//转成字符数组
			for (i = 0; i < bos.length; i++) {
				if (bos[i] == 0x0a)
					n++;
			}
			byte[] bos_new = new byte[bos.length + n];
			n = 0;
			for (i = 0; i < bos.length; i++) { // 手机中换行为0a,将其改为0d 0a后再发送
				if (bos[i] == 0x0a) {
					bos_new[n] = 0x0d;
					n++;
					bos_new[n] = 0x0a;
				} else {
					bos_new[n] = bos[i];
				}
				n++;
			}
			os.write(bos_new);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	protected void initView() {
		setContentView(R.layout.act_home);

		btnUp = (Button) findViewById(R.id.btnUp);
		btnDown = (Button) findViewById(R.id.btnDown);
		btnLeft = (Button) findViewById(R.id.btnLeft);
		btnRight = (Button) findViewById(R.id.btnRight);
		btnStop = (Button) findViewById(R.id.btnStop);

		btnRest = (Button) findViewById(R.id.btnReset);
		btnSet = (Button) findViewById(R.id.btnSet);

		btnX = (Button) findViewById(R.id.btnX);
		btnY = (Button) findViewById(R.id.btnY);
		btnA = (Button) findViewById(R.id.btnA);
		btnB = (Button) findViewById(R.id.btnB);

	}

	@Override
	protected void setListener() {
		btnUp.setOnClickListener(this);
		btnDown.setOnClickListener(this);
		btnLeft.setOnClickListener(this);
		btnRight.setOnClickListener(this);
		btnStop.setOnClickListener(this);

		btnRest.setOnClickListener(this);
		btnSet.setOnClickListener(this);

		btnX.setOnClickListener(this);
		btnY.setOnClickListener(this);
		btnA.setOnClickListener(this);
		btnB.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 左边部分按钮
		case R.id.btnUp:
			send("f");
			IToast.hint(this, R.string.btn_up);
			break;
		case R.id.btnDown:
			send("b");
			IToast.hint(this, R.string.btn_down);
			break;
		case R.id.btnLeft:
			send("l");
			IToast.hint(this, R.string.btn_left);
			break;
		case R.id.btnRight:
			send("r");
			IToast.hint(this, R.string.btn_right);
			break;
		case R.id.btnStop:
			send("s");
			IToast.hint(this, R.string.btn_stop);
			break;
		// 中间部分按钮
		case R.id.btnReset:
			IToast.hint(this, R.string.btn_reset);
			break;
		case R.id.btnSet:
			this.connectBluetooth();
			break;
		// 右边部分按钮
		case R.id.btnX:
			IToast.hint(this, R.string.btn_x);
			break;
		case R.id.btnY:
			IToast.hint(this, R.string.btn_y);
			break;
		case R.id.btnA:
			IToast.hint(this, R.string.btn_a);
			break;
		case R.id.btnB:
			IToast.hint(this, R.string.btn_b);
			break;

		default:
			break;
		}
	}

}

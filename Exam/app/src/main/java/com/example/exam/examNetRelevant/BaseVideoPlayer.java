package com.example.exam.examNetRelevant;//package com.example.exam.examNetRelevant;
//
//
//import android.annotation.SuppressLint;
//import android.app.Dialog;
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.content.pm.ActivityInfo;
//import android.os.Build;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentActivity;
//import android.support.v4.app.FragmentTransaction;
//import android.text.TextUtils;
//import android.util.DisplayMetrics;
//import android.util.Log;
//import android.util.TypedValue;
//import android.view.Gravity;
//import android.view.KeyEvent;
//import android.view.LayoutInflater;
//import android.view.MotionEvent;
//import android.view.OrientationEventListener;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.View.OnTouchListener;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.BaseAdapter;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.ListView;
//import android.widget.PopupWindow;
//import android.widget.RelativeLayout;
//import android.widget.RelativeLayout.LayoutParams;
//import android.widget.SeekBar;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.example.myapplication.OpenPDF;
//import com.example.myvideomode.MPSAlertInterface.NegativeClickListener;
//import com.example.myvideomode.MPSAlertInterface.PositiveClickListener;
//import com.example.myvideomode.NetCourseRelevant.PostData;
//
//import java.util.Timer;
//import java.util.TimerTask;
//
//import download.DownLoadService;
//import download.FileInfo;
//import io.vov.vitamio.MediaPlayer;
//import io.vov.vitamio.MediaPlayer.OnCompletionListener;
//import io.vov.vitamio.MediaPlayer.OnSeekCompleteListener;
//import io.vov.vitamio.Vitamio;
//import io.vov.vitamio.widget.CenterLayout;
//import io.vov.vitamio.widget.MediaController;
//import io.vov.vitamio.widget.MediaController.OnHiddenListener;
//import io.vov.vitamio.widget.MediaController.OnShownListener;
//import io.vov.vitamio.widget.VideoView;
//
//
//public class BaseVideoPlayer extends FragmentActivity implements IStartService, PlayVideoProxy, MediaPlayer.OnPreparedListener,
//        MediaPlayer.OnInfoListener, MediaPlayer.OnBufferingUpdateListener, OnClickListener,
//        OnCompletionListener, OnSeekCompleteListener {
//    protected static final String TAG = BaseVideoPlayer.class.getSimpleName();
//    public static MPSMonitorProxy listeners = new MPSMonitorProxy(PlayVideoProxy.class, IStartService.class);
//    //网络获取的数据的类
//    public static PostData postData;
//    public static String token = "LHHH6bwjT94h5d0sqnmQeWVg3KcCUEcReRmyeiBoehY=";
//    /*
//     * 切换全屏播放时，屏幕宽高指示变量
//     */
//    private final int FULL_WIDTH = -1, FULL_HEIGHT = -1;
//    // 播放器
//    protected VideoView mVideoView;
//    // 播放窗口大小切换按钮
//    protected ImageButton mZoomSwitchBtn;
//    protected View mPlayBtn;
//    protected TextView mVideoTitle;
//    protected TextView leaningCount;
//    TextView showSlelctTime;
//    /**
//     * 第一次播放数值为0，否则就从该位置开始开始播放
//     */
//    long isFirstPlay = 0;
//    //定时器
//    Timer timer = new Timer();
//    //下载服务的intent
//    Intent intentDownLoadService;
//    // 当前屏幕方向(默认为竖直)
//    private int mCurrentOrientation = OrientationHelper.PORTRAIT;
//    // 播放器最小窗口高度
//    private int mDefaultSmallHeight = 0;
//    /*
//     * 记录上次播放位置 场景：在播放过程，有来电或Home键被按下，记录暂停播放的位置
//     */
//    private Long mPreviousPlayPosition = 0L;
//    /*
//     * 方向传感器监听器 条件：视频正在播放且没有手动切换横竖屏
//     */
//    private OrientationEventListener mOrientationEventListener;
//    private View mRootView;
//    private SeekBar mMediaSeekBar;
//    // 视频控制条
//    private MediaController mMediaController;
//    // 视频标题栏布局
//    private RelativeLayout mMediaTopLayout;
//    // 播放器尺寸切换布局
//    private RelativeLayout mMediaScreenWrapper;
//    private ImageView mBackBtn;
//    // 缓冲UI
//    private LinearLayout mBufferLayout;
//    // 显示当前网络下行速度和缓冲百分比
//    private TextView mloadRate, mLoadPercent;
//    /*
//     * 播放速度控制相关
//     */
//    private TextView playSpeed;
//    private float controlSpeed = 1.0f;
//    private PopupWindow popupWindow;
//    private View view;
//    /*
//     * 加载图片相关
//     */
////	private ImageLoader mImageLoader = ImageLoader.getInstance();
////	private DisplayImageOptions mImageOptions;
//    private ImageView mImageCover;
//    private String mVideoUrl;
//    private String mId;
//    private boolean isEncrypt = false;
//    private boolean isLocale = false;
//    private boolean isRecord = false;
//    private int mCurrentRatio;
//    private int mUpdateRatio;
//    private SharedPreferences mVideoPrefs;
//    //	private VolleyRequestHelper mRequestHelper;
//    private boolean enterBoolean = true;
//    //popuwondow的高和宽
//    private int popupHeight;
//    private int popupWidth;
//    private FileInfo fileInfo;
//
//    protected void playVideo(String title, String url, String id, boolean isLocale, boolean isRecord) {
//
//        /**检测url */
//        if (TextUtils.isEmpty(url)) {
//            Toast.makeText(BaseVideoPlayer.this, "video path can't be empty!", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        /** 检测多次点击 */
//        if (url.equals(mVideoUrl)) {
//            return;
//        }
//
//        if (!isRecord && !TextUtils.isEmpty(mVideoUrl)) {
//            /** 保存记录并提交进�? */
//            savePauseTime();
//            /** 处理加密视频 */
////			restoreVideo();
//        }
////		if(mVideoView!=null){
////			mVideoView.stopPlayback();
////		}
//        /** 新的视频 */
//        mVideoTitle.setText(title);
//        this.mVideoUrl = url;
//        this.mId = id;
////		this.mCurrentRatio = ratio;
////		this.isEncrypt = isEncrypt;
//        this.isLocale = isLocale;
//        this.isRecord = isRecord;
//
//        if (isLocale) {
////			if (isEncrypt) {
////				boolean hasDecrypt = Tools.hasDecrypted(this, mVideoUrl);
////				if (!hasDecrypt) {
////					EncryptUtil.decryptVideo(mVideoUrl);
////					Tools.setDecryptedStatus(this, mVideoUrl, true);
////				}
////			}
//            checkVideo();
//        } else {
//            checkVideo();
//        }
//
////		else {
////			if ("Lenovo K900".equals(DEVICE_MODEL)) {
////				String end = mVideoUrl.substring(mVideoUrl.length() - 4, mVideoUrl.length());
//////				Trace.i("mzw", "the end == " + mVideoUrl);
////				if (!".mp4".equals(end)) {
////					mVideoUrl = mVideoUrl + ".gkpmp4";
////				}
////				checkVideo();
////			} else {
////				Handler handler = new Handler() {
////					@Override
////					public void handleMessage(Message msg) {
////						switch (msg.what) {
////						case Constants.ENABLE:
////							Log.v("sample", "enable url:" + mVideoUrl + ".gkpm3u8");
////							mVideoUrl = mVideoUrl + ".gkpm3u8";
////							checkVideo();
////							break;
////						case Constants.DISABLE:
////							Log.v("sample", "disable url:" + mVideoUrl + ".gkpm3u8");
////							checkVideo();
////							break;
////						}
////					}
////				};
////				GeneralTools.isUrlAvailable(mVideoUrl + ".gkpm3u8", handler);
////			}
////		}
//    }
//
//    /*
//     * 检测视频是否是第一次播放
//     */
//    private void checkVideo() {
//        long pausePosition = mVideoPrefs.getLong(mVideoUrl, 0);
//        if (pausePosition == 0) {
//            try {
//                pausePosition = (long) fileInfo.getSawTime();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        if (pausePosition > 3 * 1000) {
//            playVideo(pausePosition);
//            isFirstPlay = pausePosition;
//        } else {
//            playVideo(0);
//            isFirstPlay = 0;
//        }
//    }
//
//    /*
//     * 设置播放位置
//     */
//    private void playVideo(long time) {
//        mVideoView.setVideoPath(mVideoUrl);
//        mVideoView.requestFocus();
//        mImageCover.setVisibility(View.GONE);
//        mMediaScreenWrapper.setEnabled(true);
//        mMediaController.setVisibility(View.VISIBLE);
//        playSpeed.setText(controlSpeed + "x");
//
////		mVideoView.seekTo(0);
////		while (true){
////			if(time>=3*1000&&mVideoView.isPlaying()){
////				showPlayPositionAlert(time);
////				break;
////			}
////		}
//
//    }
//
//    private void savePauseTime() {
//        System.out.println("保存进度");
//        long position = mVideoView.getCurrentPosition();
//        mVideoPrefs.edit().putLong(mVideoUrl, position).commit();
//        postData.postUpdataCourseWareDetail("16041109-2085-9758-673d-0c4637900e0c",
//                fileInfo.getId(), (double) position, (int) (position * 100 / mVideoView.getDuration()), token);
////        fileInfo.setSawTime(position);
//        mVideoView.pause();
////        if (!isRecord && mVideoView.getDuration() > 0) {
////            if (mVideoView.getDuration() <= 2000) {
////                mUpdateRatio = 100;
////            } else {
////                int duration = (int) mVideoView.getDuration();
////                mUpdateRatio = (int) (position * 100 / (duration));
////                if (mUpdateRatio >= 95) {
////                    mUpdateRatio = 100;
////                }
////            }
////        }
//
//    }
//
////	private void restoreVideo() {
////		if (isLocale) {
////			if (isEncrypt && isLocale) {
////				EncryptUtil.encryptVideo(mVideoUrl);
////				Tools.setDecryptedStatus(this, mVideoUrl, false);
////			}
////		}
////	}
//
//    public void showPlayPositionAlert(final long time) {
//        if (this.isFinishing()) {
//            return;
//        }
//        mVideoView.pause();
//        final String formatTime = formatTime(time);
//        System.out.println("formatTime======" + formatTime + "long" + time);
//
//        MPSAlert2BDialog.Builder builder = new MPSAlert2BDialog.Builder(this);
//        builder.setTitle("是否继续播放");
//        builder.setMessage(String.format("您上次观看到：" + formatTime, formatTime));
//        builder.setOnPositiveClickListener("继续观看", new PositiveClickListener() {
//            @Override
//            public void onPositiveClick(Dialog dialog) {
//                dialog.dismiss();
//                mVideoView.seekTo(time);
//
//            }
//        });
//        builder.setOnNegativeClickListener("重新看一遍", new NegativeClickListener() {
//            @Override
//            public void onNegativeClick(Dialog dialog) {
//                dialog.dismiss();
//                mVideoView.seekTo(0);
//            }
//        });
//        builder.create().show();
//    }
//
//    /**
//     * 播放进度转换时间显示的tools
//     */
//    private String formatTime(long time) {
//        long totalSeconds = time / 1000;
//        long seconds = totalSeconds % 60;
//        long minutes = (totalSeconds / 60) % 60;
//        long hours = totalSeconds / 3600;
//        if (hours > 0) {
//            return String.format("%02d:%02d:%02d", hours, minutes, seconds).toString();
//        } else {
//            return String.format("%02d:%02d", minutes, seconds).toString();
//        }
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        Vitamio.isInitialized(getApplicationContext());
//        setContentView(R.layout.base_video_player);
//        //============初始化一个postData对象===========
//        postData = PostData.getInstence();
//
//        /**代理初始化*/
//        listeners.addListener(this);
////		mRequestHelper = new VolleyRequestHelper(BaseVideoPlayer.this, TAG);
//        mRootView = findViewById(R.id.root_view);
//        mRootView.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                popupWindow.dismiss();
//            }
//        });
//
////		String accountName = Preferences.getString(Constants.UserInfo.ACCOUNT_NAME, "gikoo");
//        mVideoPrefs = getSharedPreferences("test", Context.MODE_PRIVATE);
//
//        /**
//         * 初始化封面
//         */
////		mImageLoader.init(ImageLoaderConfiguration.createDefault(this));
////		mImageOptions = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.covertv).showImageForEmptyUri(R.drawable.covertv).showImageOnFail(R.drawable.covertv).cacheInMemory(false).cacheOnDisc(true).considerExifParams(true).bitmapConfig(Bitmap.Config.RGB_565).build();
//
//        // 初始化播放器最小高度220dp
//        mDefaultSmallHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 220, getResources().getDisplayMetrics());
//        /*
//         * 初始化头部布局控件
//		 */
//        mMediaScreenWrapper = (RelativeLayout) findViewById(R.id.screen_wrapper_layout);
//        mMediaTopLayout = (RelativeLayout) findViewById(R.id.mediacontroller_top_layout);
//        mBackBtn = (ImageView) findViewById(R.id.video_back_btn);
//        mVideoTitle = (TextView) findViewById(R.id.video_title_text);
//        leaningCount = (TextView) findViewById(R.id.leaningCount);
//        leaningCount.setOnClickListener(this);
//        mBackBtn.setOnClickListener(this);
//        /*
//         * 初始化底部布局控件
//		 */
//
//        mImageCover = (ImageView) findViewById(R.id.img_cover);
//        mImageCover.setOnClickListener(this);
//
////		String courseName = getIntent().getStringExtra(Constants.Addition.COURSE_NAME);
////		String bigCover = getIntent().getStringExtra(Constants.Addition.BIG_COVER);
////		mVideoTitle.setText(courseName);
////		mImageLoader.displayImage("云学习", mImageCover, mImageOptions);
//
//        mMediaSeekBar = (SeekBar) findViewById(R.id.mediacontroller_seekbar);
//
//        mMediaSeekBar.setOnTouchListener(new OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                mVideoView.pause();
//                return false;
//            }
//        });
//
//        mZoomSwitchBtn = (ImageButton) findViewById(R.id.mediacontroller_screen_size);
//        mPlayBtn = findViewById(R.id.mediacontroller_play_pause);
//        playSpeed = (TextView) findViewById(R.id.playSpeed);
//        mMediaController = (MediaController) findViewById(R.id.media_play_controler);
//
//
//
//		/*
//         * 滑动屏幕快进快退事件
//		 */
//        mMediaScreenWrapper.setOnTouchListener(new OnTouchListener() {
//            int x1 = 0, x2 = 0, progress = 0;
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                popupWindow.dismiss();
//
//                if (enterBoolean) {
//                    switch (event.getAction()) {
//                        case MotionEvent.ACTION_UP:
//
//                            showSlelctTime.setVisibility(View.GONE);
//                            if (progress != 0) {
////                                mMediaController.setVisibility(View.VISIBLE);
//                                mVideoView.seekTo(mVideoView.getCurrentPosition() + progress);
//                                mMediaController.show();
//                                x1 = 0;
//                                x2 = 0;
//                                progress = 0;
//                            }
//                            break;
//                        case MotionEvent.ACTION_MOVE:
//                            mVideoView.pause();
//                            mVideoView.getCurrentPosition();
//                            x2 = (int) event.getX();
//                            progress = (x2 - x1) * 100;
//                            if (mVideoView.getCurrentPosition() + progress >= 0 && mVideoView.getCurrentPosition() + progress <= mVideoView.getDuration()) {
//                                showSlelctTime.setText(formatTime(mVideoView.getCurrentPosition() + progress));
//                                mMediaSeekBar.setProgress((int) ((mVideoView.getCurrentPosition() * 1000 + progress * 1000) / mVideoView.getDuration()));
//                            } else {
//                                if (mVideoView.getCurrentPosition() + progress <= 0) {
//                                    showSlelctTime.setText("00:00");
//                                }
//                                if (mVideoView.getCurrentPosition() + progress >= mVideoView.getDuration()) {
//                                    showSlelctTime.setText(formatTime(mVideoView.getDuration()));
//                                }
//                            }
////					mMediaController.setVisibility(View.VISIBLE);
//                            showSlelctTime.setVisibility(View.VISIBLE);
//                            break;
//                        case MotionEvent.ACTION_DOWN:
//                            x1 = (int) event.getX();
//                        default:
//                            break;
//                    }
//                }
//
//                return true;
//            }
//        });
//
//		/*
//         *控制播放速度的 popuwindown
//		 */
//        final float[] mSpeed = new float[]{0.5f, 1f, 1.5f};
//        BaseAdapter adapter = new BaseAdapter() {
//            @Override
//            public long getItemId(int position) {
//
//                return position;
//            }
//
//            @Override
//            public Object getItem(int position) {
//
//                return mSpeed[position];
//            }
//
//            @Override
//            public int getCount() {
//
//                return mSpeed.length;
//            }
//
//            @Override
//            public View getView(int position, View convertView, ViewGroup parent) {
//                convertView = LayoutInflater.from(getApplicationContext()).
//                        inflate(R.layout.only_one_textview, null);
//                TextView textView = (TextView) convertView.findViewById(R.id.only_one_textview);
//                textView.setText(mSpeed[position] + "X");
//                return convertView;
//            }
//        };
//
//        view = getLayoutInflater().inflate(R.layout.popuwindow_item, null);
//        final ListView listView = (ListView) view.findViewById(R.id.listView1_liu);
//        listView.setAdapter(adapter);
//        popupWindow = new PopupWindow(view, LayoutParams.WRAP_CONTENT,
//                LayoutParams.WRAP_CONTENT, true);
//        popupWindow.setOutsideTouchable(true);
//        popupWindow.setFocusable(false);
//
//        /**播放速度调节按钮*/
//        playSpeed.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                listView.setOnItemClickListener(new OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        mVideoView.setPlayBackSpeed(mSpeed[position]);
//                        controlSpeed = mSpeed[position];
//                        popupWindow.dismiss();
//                        playSpeed.setText("" + mSpeed[position] + "x");
//                    }
//                });
//                //				popupWindow.setTouchable(true);
//                int[] location = new int[2];
//                v.getLocationOnScreen(location);
//                System.out.println(popupWidth + "===========" + popupHeight);
//                System.out.println(location[0] + "===========" + location[1]);
//                System.out.println("===========" + popupWindow.getHeight());
//                popupWindow.showAtLocation(v, Gravity.NO_GRAVITY, (location[0] + v.getWidth() / 2) - popupWidth / 2, location[1] - popupHeight);
//            }
//        });
//        /**显示与隐藏控制条*/
//        mMediaController.setOnShownListener(new OnShownListener() {
//            @Override
//            public void onShown() {
//                mMediaTopLayout.setVisibility(View.VISIBLE);
//
//            }
//        });
//        mMediaController.setOnHiddenListener(new OnHiddenListener() {
//            @Override
//            public void onHidden() {
//                mMediaTopLayout.setVisibility(View.GONE);
//                popupWindow.dismiss();
//            }
//        });
//
//        mImageCover.setVisibility(View.VISIBLE);
//        mMediaScreenWrapper.setEnabled(false);
//        mMediaController.setVisibility(View.GONE);
//        mZoomSwitchBtn.setEnabled(false);
//        mPlayBtn.setEnabled(false);
//        mZoomSwitchBtn.setOnClickListener(this);
//
//		/*
//         * 初始化播放器
//		 */
//        mVideoView = (VideoView) findViewById(R.id.surface_view);
//        mVideoView.setMediaController(mMediaController);
//        mVideoView.requestFocus();
//        mVideoView.setOnInfoListener(this);
//        mVideoView.setOnBufferingUpdateListener(this);
//        mVideoView.setOnPreparedListener(this);
//        mVideoView.setOnCompletionListener(this);
//        mVideoView.setOnSeekCompleteListener(this);
//
//        mVideoView.pause();
//
//		/*
//         * 初始化缓冲界面
//		 */
//        mBufferLayout = (LinearLayout) findViewById(R.id.mediacontroller_buffer_layout);
//        mloadRate = (TextView) findViewById(R.id.vid_load_rate);
//        mLoadPercent = (TextView) findViewById(R.id.vid_load_percent);
//        /*
//         * 显示触目屏幕跳转的时间
//		 */
//        showSlelctTime = (TextView) findViewById(R.id.showSelectTime);
//        /*
//         * 初始化方向传感器
//		 */
//        mOrientationEventListener = new OrientationEventListener(getApplicationContext()) {
//            @Override
//            public void onOrientationChanged(int orientation) {
//                //				Log.i(TAG, "onOrientationChanged" + orientation);
//                if (mVideoView.isPlaying()) {
//                    int tending = OrientationHelper.userTending(orientation, mCurrentOrientation);
//                    if (tending == OrientationHelper.NOTHING)
//                        return;
//
//                    if (!mMediaController.isShowing()) {
//                        mMediaController.show();
//                    }
//                    if (tending == OrientationHelper.LANDSCAPE) {
//                        switchToFullScreenMode();
//                        playSpeed.setVisibility(View.VISIBLE);
//                    } else if (tending == OrientationHelper.PORTRAIT) {
//                        switchToSmallScreenMode();
//                        playSpeed.setVisibility(View.GONE);
//                    }
//                }
//            }
//        };
//        Fragment fragment = new Fragment_TotalCourseNews(getBaseContext());
//        setContentFragment(fragment);
//        /**启用重力感应系统*/
//        if (mOrientationEventListener.canDetectOrientation()) {
//            mOrientationEventListener.enable();
//        }
//
//    }
//
//    @Override
//    public void onClick(View v) {
//        if (v == mBackBtn) {
//            /**返回键点击事件*/
//            onBackPressed();
//            playSpeed.setVisibility(View.GONE);
//            popupWindow.dismiss();
//        } else if (v == mZoomSwitchBtn) {
//            /**全屏小屏切换按钮*/
//            Log.e(TAG, "ZoomSwitch");
//            if (mOrientationEventListener != null) {
//                mOrientationEventListener.disable();
//            }
//            if (mCurrentOrientation == OrientationHelper.LANDSCAPE) {
//                switchToSmallScreenMode();
//                playSpeed.setVisibility(View.GONE);
//            } else if (mCurrentOrientation == OrientationHelper.PORTRAIT) {
//                switchToFullScreenMode();
//                playSpeed.setVisibility(View.VISIBLE);
//            }
//        } else if (v == mImageCover) {
//            iPlayFirstVideo a = Fragment_CourseList.listeners.getListener();
//            if (a != null) {
//                a.playFirstVideo();
//            }
//        } else if (v == leaningCount) {
//            Intent intent = new Intent(this, OpenPDF.class);
//            startActivity(intent);
//        }
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        //		mVideoView.setVolume(1.0f, 1.0f);
//        mVideoView.seekTo(mPreviousPlayPosition);
//        mVideoView.pause();
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        if (mVideoView.isPlaying()) {
//            //mVideoView.setVolume(0.0f, 0.0f);
//            mVideoView.pause();
//            mPreviousPlayPosition = mVideoView.getCurrentPosition();
//        }
//
//        if (!isRecord && !TextUtils.isEmpty(mVideoUrl)) {
//            /** 保存记录并提交进�? */
//            savePauseTime();
//            /** 处理加密视频 */
////			restoreVideo();
//        }
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        try {
//            // 禁用方向传感器
//            if (mOrientationEventListener != null) {
//                mOrientationEventListener.disable();
//            }
//            if (intentDownLoadService != null) {
//                stopService(intentDownLoadService);
//            }
//            mVideoView.destroyDrawingCache();
//            mVideoView.stopPlayback();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void onBackPressed() {
//
//        if (mCurrentOrientation == OrientationHelper.LANDSCAPE) {
//            switchToSmallScreenMode();
//            return;
//        } else {
//            mVideoView.setVolume(0.0f, 0.0f);
//        }
//        super.onBackPressed();
//    }
//
//    /**
//     * 视频播放时信息的捕获
//     */
//    @Override
//    public boolean onInfo(MediaPlayer mp, int what, int extra) {
//
//        switch (what) {
//            case MediaPlayer.MEDIA_INFO_BUFFERING_START:
//                mBufferLayout.setVisibility(View.VISIBLE);
//                mloadRate.setText(null);
//                mLoadPercent.setText(null);
//                mPlayBtn.setEnabled(false);
//                break;
//            case MediaPlayer.MEDIA_INFO_BUFFERING_END:
//                mBufferLayout.setVisibility(View.GONE);
//
//                if (mCurrentOrientation == OrientationHelper.LANDSCAPE) {
//                    switchToFullScreenMode();
//                } else {
//                    switchToSmallScreenMode();
//                }
//
//                mVideoView.start();
//                mVideoView.setPlayBackSpeed(controlSpeed);
//                playSpeed.setText(controlSpeed + "x");
////			}
//                // setPlayScreenSize(FULL_WIDTH, mDefaultSmallHeight);
//                mZoomSwitchBtn.setEnabled(true);
//                mPlayBtn.setEnabled(true);
//                break;
//            case MediaPlayer.MEDIA_INFO_DOWNLOAD_RATE_CHANGED:
//                mloadRate.setText(extra + "kb/s" + "  ");
//                break;
//        }
//        return true;
//    }
//
//    @Override
//    public void onPrepared(MediaPlayer mediaPlayer) {
//        mediaPlayer.setPlaybackSpeed(controlSpeed);
//        mediaPlayer.seekTo(0);
//        mMediaScreenWrapper.setFocusableInTouchMode(true);
//        enterBoolean = true;
//        playSpeed.setText(controlSpeed + "x");
//        if (mCurrentOrientation == OrientationHelper.LANDSCAPE) {
//            switchToFullScreenMode();
//        } else {
//            switchToSmallScreenMode();
//        }
//        if (isFirstPlay >= 3 * 1000) {
//            showPlayPositionAlert(isFirstPlay);
//        } else {
//            mediaPlayer.seekTo(0);
//        }
//        //开始播放的时候创建一个计时器来判断播放的时间
//
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                if (mVideoView.isPlaying() && true) {
//
//                    System.out.println(formatTime(mVideoView.getCurrentPosition()) + "播放的时间"
//                            + mVideoView.getCurrentPosition());
//                    if ((formatTime(mVideoView.getCurrentPosition()).equals("02:00"))) {
//                        System.out.println("这就是2分钟的进度" + mVideoView.getCurrentPosition());
//                    }
//                }
//            }
//        }, 1000, 1000);
//    }
//
//    @Override
//    public void onBufferingUpdate(MediaPlayer mp, int percent) {
//        mLoadPercent.setText(percent + "%");
//    }
//
//    /**
//     * 切换到全屏播放
//     *
//     * @author liuhuang
//     */
//    @SuppressLint("NewApi")
//    private void switchToFullScreenMode() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//            mRootView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
//        }
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
//        setPlayScreenSize(FULL_WIDTH, FULL_HEIGHT);
//        mCurrentOrientation = OrientationHelper.LANDSCAPE;
//        mZoomSwitchBtn.setImageResource(R.drawable.ic_vidcontrol_fullscreen_on);
//    }
//
//    /**
//     * 切换到小屏播放
//     *
//     * @author liuhuang
//     */
//    @SuppressLint("NewApi")
//    private void switchToSmallScreenMode() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//            mRootView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
//        }
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        setPlayScreenSize(FULL_WIDTH, mDefaultSmallHeight);
//        mCurrentOrientation = OrientationHelper.PORTRAIT;
//        mZoomSwitchBtn.setImageResource(R.drawable.ic_vidcontrol_fullscreen_off);
//        playSpeed.setVisibility(View.GONE);
//    }
//
//    /**
//     * 设置切换播放模式时的布局参数 </br>
//     *
//     * @param width  宽度
//     * @param height 高度
//     * @author liuhuang
//     */
//    private void setPlayScreenSize(int width, int height) {
//        DisplayMetrics metrics = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(metrics);
//        LayoutParams headerParams = (LayoutParams) mMediaScreenWrapper.getLayoutParams();
//        CenterLayout.LayoutParams videoParams = (CenterLayout.LayoutParams) mVideoView.getLayoutParams();
//
//        int screenWidth = (width == FULL_WIDTH ? metrics.widthPixels : width);
//        headerParams.width = screenWidth;
//        videoParams.width = screenWidth;
//
//        int screenHeight = (height == FULL_HEIGHT ? metrics.heightPixels : height);
//        headerParams.height = screenHeight;
//        videoParams.height = screenHeight;
//
//        mMediaScreenWrapper.setLayoutParams(headerParams);
//        mMediaScreenWrapper.requestLayout();
//
//        mVideoView.setLayoutParams(videoParams);
//        mVideoView.requestFocus();
//        mVideoView.requestLayout();
//    }
//
//    /**
//     * 视频下方布局
//     */
//    protected void setContentFragment(Fragment fragment) {
//        if (fragment == null)
//            return;
//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        if (fragment.isAdded()) {
//            ft.show(fragment);
//        } else {
//            ft.add(R.id.content_layout, fragment);
//        }
//        ft.commitAllowingStateLoss();
//    }
//
////	public void submitVideoRatio(final String id, final int ratio) {
////
////		if (!NetStateService.getState()) {
////			Log.v("stemp", "update ratio=" + ratio + " local.");
////			SharedPreferences ratioPref = NetStateService.getRatioPreferences();
////			if (ratioPref != null) {
////				ratioPref.edit().putInt(id, ratio).commit();
////			}
////			return;
////		}
//
////		Listener<JSONObject> listener = new Listener<JSONObject>() {
////			@Override
////			public void onResponse(JSONObject arg0) {
////				SharedPreferences ratioPref = NetStateService.getRatioPreferences();
////				ratioPref.edit().remove(id).commit();
////
////				OnDataChangedListener listener = HUAWEI_CourseListFragment.listeners.getListener();
////				if (listener != null) {
////					Log.e(TAG, "onResponse:" + id + "|" + ratio);
////					listener.onDataChanged(id, ratio);
////				}
////			}
////		};
////
////		Log.v(TAG, "update ratio=" + ratio + " net.");
////		String url = AppConfig.getHost() + "notification/user-task/" + id + "/";
////		Map<String, Object> params = new HashMap<String, Object>();
////		params.put("ratio", String.valueOf(ratio));
////		mRequestHelper.getJSONObject4Put(url, params, Constants.DEFAULT_TIMEOUT, true, listener, null);
////	}
//
//    @Override
//    public void onCompletion(MediaPlayer mp) {
//        try {
//            switchToSmallScreenMode();
//            if (!isRecord && !TextUtils.isEmpty(mVideoUrl)) {
//                /** 保存记录并提交进�? */
//                savePauseTime();
//                /** 处理加密视频 */
////			restoreVideo();
//            }
//            fileInfo.setIsSaw(true);
//            Fragment_CourseList.adapter.notifyDataSetChanged();
//            mp.reset();
//            popupWindow.dismiss();
//            iVideoCompleteListener a = Fragment_CourseList.listeners.getListener();
//            if (a != null) {
//                mVideoView.stopPlayback();
//                a.VideoCompleteListener(mVideoView);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void onSeekComplete(MediaPlayer mp) {
//
//    }
//
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//
//        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
//            playSpeed.setVisibility(View.GONE);
//            popupWindow.dismiss();
//            onBackPressed();
//            return true;
//        }
//        return false;
//
//    }
//
//    @Override
//    public void iPlayVideo(Bundle bundle) {
//        fileInfo = (FileInfo) bundle.getSerializable("fileInfo");
//        playVideo(fileInfo.getFileName(), bundle.getString("url")
//                , "aaa", false, false);
//    }
//
//    @Override
//    public void startService(FileInfo fileInfo, String start) {
//        /**
//         * 初始化下载服务
//         */
//        System.out.println("开始启动服务");
//        intentDownLoadService = new Intent(this, DownLoadService.class);
//        intentDownLoadService.setAction(start);
//        intentDownLoadService.putExtra("fileInfo", fileInfo);
//        startService(intentDownLoadService);
//    }
//
//
//}

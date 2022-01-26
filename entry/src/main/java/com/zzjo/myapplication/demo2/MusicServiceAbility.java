package com.zzjo.myapplication.demo2;

import ohos.aafwk.ability.Ability;
import ohos.aafwk.ability.LocalRemoteObject;
import ohos.aafwk.content.Intent;
import ohos.event.notification.NotificationRequest;
import ohos.media.audio.AudioRenderer;
import ohos.media.audio.AudioRendererInfo;
import ohos.media.audio.AudioStreamInfo;
import ohos.rpc.IRemoteObject;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

public class MusicServiceAbility extends Ability {
    private static final HiLogLabel LABEL_LOG = new HiLogLabel(3, 0xD001100, "Demo");
    private AudioRenderer renderer;

    @Override
    public void onStart(Intent intent) {
        HiLog.error(LABEL_LOG, "MusicServiceAbility::onStart");
        super.onStart(intent);
        // 创建通知，其中1005为notificationId
        NotificationRequest request = new NotificationRequest(1006);
        NotificationRequest.NotificationNormalContent content = new NotificationRequest.NotificationNormalContent();
//        String musicName = intent.getParams().getParam("musicName").toString();
        content.setTitle("gogo音乐").setText("正在播放：朱砂痣与白月光");
        NotificationRequest.NotificationContent notificationContent = new NotificationRequest.NotificationContent(content);
        request.setContent(notificationContent);

        // 绑定通知，1005为创建通知时传入的notificationId
        this.keepBackgroundRunning(1006, request);
        AudioStreamInfo audioStreamInfo = new AudioStreamInfo.Builder().sampleRate(44100) // 44.1kHz
                .audioStreamFlag(AudioStreamInfo.AudioStreamFlag.AUDIO_STREAM_FLAG_MAY_DUCK) // 混音
                .encodingFormat(AudioStreamInfo.EncodingFormat.ENCODING_PCM_16BIT) // 16-bit PCM
                .channelMask(AudioStreamInfo.ChannelMask.CHANNEL_OUT_STEREO) // 双声道输出
                .streamUsage(AudioStreamInfo.StreamUsage.STREAM_USAGE_MEDIA) // 媒体类音频
                .build();
        AudioRendererInfo audioRendererInfo = new AudioRendererInfo.Builder().audioStreamInfo(audioStreamInfo)
                .audioStreamOutputFlag(AudioRendererInfo.AudioStreamOutputFlag.AUDIO_STREAM_OUTPUT_FLAG_DIRECT_PCM) // pcm格式的输出流
                .bufferSizeInBytes(100)
                .isOffload(false) // false表示分段传输buffer并播放，true表示整个音频流一次性传输到HAL层播放
                .build();
        renderer = new AudioRenderer(audioRendererInfo, AudioRenderer.PlayMode.MODE_STATIC);
        System.out.println("------>>>>>>onStart");
    }

    @Override
    public void onStop() {
        super.onStop();
        HiLog.info(LABEL_LOG, "MusicServiceAbility::onStop");
        System.out.println("------>>>>>>onStop");
        cancelBackgroundRunning();
        renderer.release();
    }

    @Override
    public void onCommand(Intent intent, boolean restart, int startId) {
        renderer.start();
        System.out.println("------>>>>>>onCommand");
    }

    @Override
    public IRemoteObject onConnect(Intent intent) {
        System.out.println("------>>>>>>onConnect");
        return new LocalRemoteObject() {};
    }

    @Override
    public void onDisconnect(Intent intent) {
        System.out.println("------>>>>>>onDisconnect");
    }
}
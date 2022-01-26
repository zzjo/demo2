package com.zzjo.myapplication.demo2.slice;

import com.zzjo.myapplication.demo2.ResourceTable;
import ohos.aafwk.ability.AbilityForm;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.ability.IAbilityConnection;
import ohos.aafwk.ability.IAbilityContinuation;
import ohos.aafwk.ability.delegation.IAbilityDelegator;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.backgroundtaskmgr.BackgroundTaskManager;
import ohos.bundle.ElementName;
import ohos.event.notification.NotificationRequest;
import ohos.rpc.IRemoteObject;

public class ShopcartAbilitySlice extends AbilitySlice {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_shopcart);


        Intent intent1 = new Intent();
        Operation operation = new Intent.OperationBuilder()
                .withDeviceId("")
                .withBundleName("com.zzjo.myapplication.demo2")
                .withAbilityName("com.zzjo.myapplication.demo2.MusicServiceAbility")
                .build();
        intent1.setOperation(operation);
        intent1.setParam("musicName", "朱砂痣与白月光");
        Button btn = (Button) findComponentById(ResourceTable.Id_playBtnStart);
        btn.setClickedListener(listener -> {
            startAbility(intent1);
        });
        Button btnConn = (Button) findComponentById(ResourceTable.Id_playBtnCommon);
        IAbilityConnection connection = new IAbilityConnection() {
            @Override
            public void onAbilityConnectDone(ElementName elementName, IRemoteObject iRemoteObject, int i) {
                System.out.println("---------连接目标MusicServiceAbility成功");
            }

            @Override
            public void onAbilityDisconnectDone(ElementName elementName, int i) {
                System.out.println("---------连接目标MusicServiceAbilityb失败");
            }
        };
        btnConn.setClickedListener(component -> {
            this.connectAbility(intent1, connection);
        });
        Button stopBtn = (Button) findComponentById(ResourceTable.Id_playBtnStop);
        stopBtn.setClickedListener(component -> {
            if (component != null) {
                this.disconnectAbility(connection);
            }
        });
        Button killBtn = (Button) findComponentById(ResourceTable.Id_playBtnKill);
        killBtn.setClickedListener(component -> {
            if (component != null) {
                this.stopAbility(intent1);
            }
        });
    }


    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }
}

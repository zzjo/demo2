package com.zzjo.myapplication.demo2.slice;

import com.zzjo.myapplication.demo2.ResourceTable;
import com.zzjo.myapplication.demo2.ShopcartAbility;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.components.Button;
import ohos.agp.components.Component;

public class MainAbilitySlice extends AbilitySlice {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);
        Button btn1 = (Button) findComponentById(ResourceTable.Id_btn1);
        btn1.setClickedListener(listener -> navigateToAnotherPage(listener));
    }

    private void navigateToAnotherPage(Component listener) {
        Intent intent = new Intent();
        Operation operation = new Intent.OperationBuilder()
                .withDeviceId("")
                .withBundleName("com.zzjo.myapplication.demo2")
                .withAbilityName(ShopcartAbility.class)
                .build();
        intent.setOperation(operation);
        this.startAbility(intent);
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

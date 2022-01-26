package com.zzjo.myapplication.demo2;

import com.zzjo.myapplication.demo2.slice.ShopcartAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;

public class ShopcartAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(ShopcartAbilitySlice.class.getName());
    }
}

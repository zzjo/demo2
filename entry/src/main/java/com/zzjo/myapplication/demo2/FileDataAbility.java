package com.zzjo.myapplication.demo2;

import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;
import ohos.data.DatabaseHelper;
import ohos.data.rdb.RdbOpenCallback;
import ohos.data.rdb.RdbStore;
import ohos.data.rdb.StoreConfig;
import ohos.data.resultset.ResultSet;
import ohos.data.rdb.ValuesBucket;
import ohos.data.dataability.DataAbilityPredicates;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.rpc.MessageParcel;
import ohos.utils.net.Uri;
import ohos.utils.PacMap;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;

public class FileDataAbility extends Ability {
    private static final HiLogLabel LABEL_LOG = new HiLogLabel(3, 0xD001100, "Demo");
    private RdbStore rdbStore;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        HiLog.info(LABEL_LOG, "FileDataAbility onStart");
        DatabaseHelper helper = new DatabaseHelper(this);
        StoreConfig config = StoreConfig.newDefaultConfig("UserStore.db");
        RdbOpenCallback callback = new RdbOpenCallback() {
            @Override
            public void onCreate(RdbStore rdbStore) {
                
            }

            @Override
            public void onUpgrade(RdbStore rdbStore, int i, int i1) {

            }
        };
        rdbStore = helper.getRdbStore(config, 1, callback);
    }

    @Override
    public ResultSet query(Uri uri, String[] columns, DataAbilityPredicates predicates) {
        return null;
    }

    @Override
    public int insert(Uri uri, ValuesBucket value) {
        HiLog.info(LABEL_LOG, "FileDataAbility insert");
        return 999;
    }

    @Override
    public int delete(Uri uri, DataAbilityPredicates predicates) {
        return 0;
    }

    @Override
    public int update(Uri uri, ValuesBucket value, DataAbilityPredicates predicates) {
        return 0;
    }

    @Override
    public FileDescriptor openFile(Uri uri, String mode) {
        File file = new File(uri.getDecodedPathList().get(0)); //get(0)?????????URI????????????????????????????????????
        if (mode == null || !"rw".equals(mode)) {
            file.setReadOnly();
        }
        FileDescriptor fd = null;
        try {
            FileInputStream fileIs = new FileInputStream(file);
            fd = fileIs.getFD();
        } catch (IOException e) {
            HiLog.info(LABEL_LOG, "failed to getFD");
        }

        // ?????????????????????
        return MessageParcel.dupFileDescriptor(fd);
    }

    @Override
    public String[] getFileTypes(Uri uri, String mimeTypeFilter) {
        return new String[0];
    }

    @Override
    public PacMap call(String method, String arg, PacMap extras) {
        return null;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }
}
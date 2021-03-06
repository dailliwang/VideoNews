package feicuiedu.com.videonews.ui.local;


import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import feicuiedu.com.videonews.R;

/**
 * 本地视频页面，使用{@link CursorLoader}来加载本地视频数据。
 */
public class LocalVideoFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private Unbinder unbinder;

    @BindView(R.id.gridView) GridView gridView;

    private LocalVideoAdapter localVideoAdapter;

    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        localVideoAdapter = new LocalVideoAdapter(getContext());
        getLoaderManager().initLoader(0, null, this);
    }

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_local_video, container, false);
    }

    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        gridView.setAdapter(localVideoAdapter);
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override public void onDestroy() {
        super.onDestroy();
        localVideoAdapter.release();
    }

    @Override public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {MediaStore.Video.Media._ID, // 视频Id
                MediaStore.Video.Media.DATA, // 视频文件路径
                MediaStore.Video.Media.DISPLAY_NAME, // 视频名称
        };
        return new CursorLoader(getContext(), MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                projection, null, null, null);
    }

    @Override public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        localVideoAdapter.swapCursor(data);

    }

    @Override public void onLoaderReset(Loader<Cursor> loader) {
        localVideoAdapter.swapCursor(null);
    }

}

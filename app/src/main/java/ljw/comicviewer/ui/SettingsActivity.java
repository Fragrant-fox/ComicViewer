package ljw.comicviewer.ui;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import ljw.comicviewer.Global;
import ljw.comicviewer.R;
import ljw.comicviewer.bean.Comic;
import ljw.comicviewer.http.ComicFetcher;
import ljw.comicviewer.http.ComicService;
import ljw.comicviewer.rule.RuleFetcher;
import ljw.comicviewer.rule.RuleParser;
import ljw.comicviewer.store.RuleStore;
import ljw.comicviewer.util.FileUtil;

public class SettingsActivity extends AppCompatActivity
        implements ComicService.RequestCallback {
    private String TAG = SettingsActivity.class.getSimpleName()+"----";
    private static Context context;
    private String rule;
    @BindView(R.id.nav_child_title)
    TextView title;
    @BindView(R.id.debug)
    TextView debug;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        context = this;
        ButterKnife.bind(this);
        initView();

        loadComicInformation();
    }

    private void initView(){
        title.setText(R.string.mine_setting);
    }

    private void getListItems(int page){
        ComicService.get().getListItems(this,page);
    }

    public void loadSearch(String keyword, int page){
        ComicService.get().getComicSearch(this,keyword,page);
    }

    public void loadComicInformation(){
         ComicService.get().getComicInfo(this,"8788");//18X id:"8788");"16058"
    }

    //按标题栏返回按钮
    public void onBack(View view) {
        finish();
    }

    @Override
    public void onFinish(Object data, String what) {
        switch (what) {
            case Global.REQUEST_COMICS_LIST:
            case Global.REQUEST_COMICS_SEARCH:
                List<Comic> comics = (List<Comic>) ComicFetcher.getSearchResults(data.toString()).getObj();
                debug.setText("");
                for (Comic comic:comics){
                    debug.append(comic.toString()+"\n");
                }
                break;
            case Global.REQUEST_COMICS_INFO:
                Comic comic = new Comic();
                comic.setId("8788");
                comic.setScore("10");
                ComicFetcher.getComicDetails(data.toString(),comic);
                debug.setText(comic.toString());
                break;
        }
    }

    @Override
    public void onError(String msg, String what) {

    }
}

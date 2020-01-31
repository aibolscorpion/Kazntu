package kz.almaty.satbayevuniversity.ui.umkd.filefragment.fileDataFragment.webViewFragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import kz.almaty.satbayevuniversity.R;
import kz.almaty.satbayevuniversity.data.App;
import kz.almaty.satbayevuniversity.data.entity.umkd.Course;
import kz.almaty.satbayevuniversity.databinding.WebViewFragmentBinding;

public class WebViewFragment extends Fragment {

    public WebView mWebView;
    private Toolbar toolbar;
    private Course course;
    private ImageView imageView;
    private WebViewViewModel mViewModel;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private WebViewFragmentBinding webViewFragmentBinding;
    ProgressBar progressBar;
    public static WebViewFragment newInstance() {
        return new WebViewFragment();
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        webViewFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.web_view_fragment, container, false);
        View v = webViewFragmentBinding.getRoot();
        progressBar =  v.findViewById(R.id.web_view_progress_bar);
        toolbar = v.findViewById(R.id.toolbarWebView);
        mWebView = v.findViewById(R.id.webViewFragment);
        imageView = v.findViewById(R.id.shareLink);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setDomStorageEnabled(true);

        progressBar.setVisibility(View.VISIBLE);

        mWebView.setVisibility(View.GONE);
        mWebView.setWebViewClient(new WebViewClient()
        {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);

          }
          @Override
          public void onPageFinished(WebView view, String url) {
              super.onPageFinished(view, url);
              progressBar.setVisibility(View.INVISIBLE);
              mWebView.setVisibility(View.VISIBLE);

          }
      }
        );
        return v;

    }



    @Override
    public void onResume(){
        super.onResume();

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener((view, i, keyEvent) -> {
            if(i == KeyEvent.KEYCODE_BACK && keyEvent.getAction() == KeyEvent.ACTION_UP){
                if (getFragmentManager().getBackStackEntryCount() > 0) {
                    getFragmentManager().popBackStackImmediate();
                }else{
                    return false;
                }
                return true;
            }
            return false;
        });

    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(WebViewViewModel.class);
        webViewFragmentBinding.setWebView(mViewModel);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            course= (Course) bundle.getSerializable("WebViewFragment");
            if(course.getFileName()!=null && !TextUtils.isEmpty(course.getFileName()))
                toolbar.setTitle(course.getFileName());
        }

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.close_file_icon);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        imageView.setOnClickListener(v -> {
            sendFile();
        });

        toolbar.setNavigationOnClickListener(v -> getFragmentManager().popBackStackImmediate());

        // TODO: Use the ViewModel
        mWebView.loadUrl("https://docs.google.com/gview?embedded=true&url=http://ssomobile.satbayev.university/api/File/Download?fileID="+ course.getId());
    }

    private void sendFile() {
        mViewModel.getDownloadFileLiveData().observe(this, file -> {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.setType("application/msword");
            sendIntent.putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(App.getContext(),"kz.almaty.satbayevuniversity.provider",file));
            startActivity(sendIntent);
        });
        mViewModel.getFileFromServer();
    }

}


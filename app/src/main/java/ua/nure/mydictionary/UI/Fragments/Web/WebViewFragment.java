package ua.nure.mydictionary.UI.Fragments.Web;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.net.MalformedURLException;
import java.net.URL;

import ua.nure.mydictionary.R;

public class WebViewFragment extends Fragment implements InternetBrowserFragment.OnPageRefresh, InternetBrowserFragment.GetHtml {
    private static final String ARG_ADDRESS_STRING = "ARG_ADDRESS_STRING";
    private String mAddress;
    private String mHtmlText;
    private WebView mWebView;


    public static WebViewFragment newInstance(String address) {
        WebViewFragment fragment = new WebViewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ADDRESS_STRING, address);
        fragment.setArguments(args);
        return fragment;
    }

    public WebViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_browser, container, false);
        mWebView = (WebView) rootView.findViewById(R.id.browser_web_view);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.addJavascriptInterface(new LoadListener(), "HTMLOUT");
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                view.loadUrl("javascript:window.HTMLOUT.processHTML('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');");
            }
        });
        openAddress(mAddress);
        return rootView;
    }

    private void openAddress(String address) {
        if (address != null && address.length() > 0) {
            if (!address.subSequence(0, 6).equals("http://")) {
                address = "http://" + address;
            }
            mWebView.getSettings().setJavaScriptEnabled(true);
            URL url = null;
            try {
                url = new URL(address);
            } catch (MalformedURLException ex) {
                Log.e("APP_URL_EXCEPTION", ex.getMessage());
            }
            if (url != null) {
                mWebView.loadUrl(url.toString());
            }
        }
    }

    @Override
    public void refreshPage() {
        mWebView.reload();
    }

    @Override
    public String getHtml() {
        return mHtmlText;
    }

    class LoadListener {
        @android.webkit.JavascriptInterface
        public void processHTML(String html) {
            Log.e("result", html);
            mHtmlText = html;
        }
    }
}

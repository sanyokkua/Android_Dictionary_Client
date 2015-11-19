package ua.kostenko.mydictionary.UI.Fragments.Web;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageButton;

import com.afollestad.materialdialogs.MaterialDialog;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import ua.kostenko.mydictionary.R;
import ua.kostenko.mydictionary.UI.Fragments.Web.AdditionItems.Bookmark;
import ua.kostenko.mydictionary.UI.CommonClasses.DataAccess.BookmarkDataAccess;
import ua.kostenko.mydictionary.UI.CommonClasses.ToolbarHandler;

public class WebViewFragment extends Fragment implements InternetBrowserFragment.OnPageRefresh, InternetBrowserFragment.GetHtml {
    private static final String ARG_ADDRESS_STRING = "ARG_ADDRESS_STRING";
    private String mAddress;
    private String mHtmlText;
    private WebView mWebView;
    private Toolbar mToolbar;
    private EditText mAddressEditText;
    private ImageButton mAddBookmarkImageButton;

    public WebViewFragment() {
        // Required empty public constructor
    }

    public static WebViewFragment newInstance(String address) {
        WebViewFragment fragment = new WebViewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ADDRESS_STRING, address);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_browser, container, false);
        mToolbar = ToolbarHandler.getToolbar(getActivity());
        mAddressEditText = ToolbarHandler.getEditText(mToolbar);
        mAddBookmarkImageButton = ToolbarHandler.getBookmarkImageButton(mToolbar);
        mAddBookmarkImageButton.setVisibility(View.VISIBLE);
        mAddBookmarkImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveBookmark();
            }
        });
        mWebView = (WebView) rootView.findViewById(R.id.browser_web_view);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.addJavascriptInterface(new LoadListener(), "HTMLOUT");
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                view.loadUrl("javascript:window.HTMLOUT.processHTML" +
                        "('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');");
                mAddressEditText.setText(mWebView.getUrl());
            }
        });
        Bundle args = getArguments();
        if (args != null) {
            mAddress = args.getString(ARG_ADDRESS_STRING);
            openAddress(mAddress);
        } else Log.d("BUNDLE_IN_WEB_VIEW", "Bundle is null");
        return rootView;
    }

    private void saveBookmark() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_bookmark, null, false);
        final EditText name = (EditText) dialogView.findViewById(R.id.dialog_add_bookmark_name_edit_text);
        final EditText url = (EditText) dialogView.findViewById(R.id.dialog_add_bookmark_url_edit_text);
        url.setText(mWebView.getUrl().substring(6));
        new MaterialDialog.Builder(getActivity())
                .customView(dialogView, false)
                .positiveText(getActivity().getResources()
                        .getString(R.string.bookmark_add))
                .negativeText(getActivity().getResources()
                        .getString(R.string.std_cancel))
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        if (url.getText().length() > 0 && name.getText().length() > 0) {
                            BookmarkDataAccess saver = new BookmarkDataAccess(getActivity());
                            saver.addAndSave(new Bookmark(url.getText().toString(), name.getText().toString()));
                            Snackbar.make(getView(), R.string.browser_bookmark_added, Snackbar.LENGTH_LONG).show();
                            dialog.cancel();
                        } else {
                            if (url.getText().length() == 0) url.requestFocus();
                            if (name.getText().length() == 0) name.requestFocus();
                        }
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        super.onNegative(dialog);
                    }
                }).show();
    }

    private void openAddress(String address) {
        URI uri = URI.create(address);
        uri = getUri(address, uri);
        URL url = getUrl(uri);
        openURL(url);
    }

    private URI getUri(String address, URI uri) {
        if ("".equals(uri.getSchemeSpecificPart())) {
            address = "http://" + address;
            uri = URI.create(address);
        }
        return uri;
    }

    private URL getUrl(URI uri) {
        URL url = null;
        try {
            url = new URL("http", uri.getSchemeSpecificPart(), "");
        } catch (MalformedURLException ex) {
            Log.e("APP_URL_EXCEPTION", ex.getMessage());
        }
        return url;
    }

    private void openURL(URL url) {
        if (url != null) {
            mWebView.loadUrl(url.toString());
            mAddressEditText.setText(url.toString());
        } else {
            mWebView.stopLoading();
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

    private class LoadListener {
        @android.webkit.JavascriptInterface
        public void processHTML(String html) {
            Log.d("result", html);
            mHtmlText = html;
        }
    }
}

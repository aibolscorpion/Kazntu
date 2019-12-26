package kz.almaty.satbayevuniversity.ui.settings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import kz.almaty.satbayevuniversity.AuthViewModel;
import kz.almaty.satbayevuniversity.R;
import kz.almaty.satbayevuniversity.data.SharedPrefCache;
import kz.almaty.satbayevuniversity.data.entity.Language;
import kz.almaty.satbayevuniversity.ui.HomeActivity;
import kz.almaty.satbayevuniversity.ui.settings.languageFragment.LanguageFragment;
import kz.almaty.satbayevuniversity.utils.LocaleHelper;
import kz.almaty.satbayevuniversity.utils.Storage;
import kz.almaty.satbayevuniversity.databinding.SettingsFragmentBinding;
import kz.almaty.satbayevuniversity.ui.LoginActivity;
import kz.almaty.satbayevuniversity.ui.settings.complaintFragment.ComplaintFragment;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class SettingsFragment extends Fragment {
    private SettingsFragmentBinding settingsFragmentBinding;
    private ConstraintLayout constraintLayout, settingsLanguage;
    private Button settingsLoginBtn;
    public Toolbar toolbar;
    private SharedPrefCache sharedPrefCache = new SharedPrefCache();
    private Context context;

    public SettingsFragment(Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        settingsFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.settings_fragment, container, false);
        View view = settingsFragmentBinding.getRoot();
        constraintLayout = view.findViewById(R.id.settingsComplaint);
        settingsLoginBtn = view.findViewById(R.id.settingsLoginBtn);
        settingsLanguage = view.findViewById(R.id.settingsLanguage);
        toolbar = view.findViewById(R.id.settingsToolbar);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        AuthViewModel authViewModel = ViewModelProviders.of(this).get(AuthViewModel.class);
        settingsFragmentBinding.setAccountEntity(Storage.getInstance());
        settingsFragmentBinding.setSettings(new SettingsFragment(context));
        authViewModel.getImageUrl();
        authViewModel.getDrawable().observe(this, bitmap -> {
            settingsFragmentBinding.setImageUrl(bitmap);
        });
        constraintLayout.setOnClickListener(v -> {
            ComplaintFragment complaintFragment = new ComplaintFragment();
            if (getFragmentManager() != null) {
                complaintFragment.show(getFragmentManager(), "Dialog");
            }
        });
        settingsLanguage.setOnClickListener(v -> {
            LanguageFragment languageFragment = new LanguageFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, languageFragment );
            transaction.addToBackStack(null);
            transaction.commit();
        });
        settingsLoginBtn.setOnClickListener(v -> {
         authViewModel.clearDB();
         Intent intent = new Intent(getActivity(), LoginActivity.class);
         startActivity(intent);
        });

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.settings);

        toolbar.setNavigationOnClickListener(v -> {
            ((HomeActivity)getActivity()).OpenToggleNavMenu();
        });
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // TODO: Use the ViewModel
        if(!sharedPrefCache.getStr("language", context).isEmpty()){
            String sharedString = sharedPrefCache.getStr("language", context);
            Gson gson = new Gson();
            try {
                Language language = gson.fromJson(sharedString, Language.class);
                LocaleHelper.setLocale(getActivity(),language.getLanguageCode());
            } catch (IllegalStateException | JsonSyntaxException ignored){}
        } else{
            LocaleHelper.setLocale(getActivity(), "ru");
        }
    }
}




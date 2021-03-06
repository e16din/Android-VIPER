package victoriaslmn.android.viper.sample.presentation.main.messages;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import victoriaslmn.android.viper.sample.R;
import victoriaslmn.android.viper.sample.domain.contacts.Contact;
import victoriaslmn.android.viper.sample.presentation.common.BasePresenter;
import victoriaslmn.android.viper.sample.presentation.common.Layout;
import victoriaslmn.android.viper.sample.presentation.main.MainActivity;
import victoriaslmn.android.viper.sample.presentation.main.common.BaseMainFragment;

@Layout(id = R.layout.recycler_view)
public class MessagesFragment extends BaseMainFragment implements MessagesView {
    private static final String CONTACT = "CONTACT";

    @Inject
    MessagesPresenter messagesPresenter;

    @Bind(R.id.chats_recycler_view)
    RecyclerView recyclerView;

    private MessagesAdapter messagesAdapter;
    private String title = null;

    public static MessagesFragment newInstance(Contact contact) {
        MessagesFragment fragment = new MessagesFragment();
        Bundle args = new Bundle();
        args.putSerializable(CONTACT, contact);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Contact contact = (Contact) getArguments().getSerializable(CONTACT);
        assert contact != null;
        messagesPresenter.init(contact);
    }

    @NonNull
    @Override
    protected BasePresenter getPresenter() {
        return messagesPresenter;
    }

    @Override
    protected void inject() {
        getMainActivityComponent().inject(this);
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public int getFabButtonIcon() {
        return 0;
    }

    @Override
    public View.OnClickListener getFabButtonAction() {
        return null;
    }

    @Override
    public void resolveTitle(String title) {
        this.title = title;
        ((MainActivity) getActivity()).resolveToolbar(this);
    }

    @Override
    public void setMessages(List<MessageViewModel> viewModels) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        messagesAdapter = new MessagesAdapter(viewModels);
        recyclerView.setAdapter(messagesAdapter);
    }

    @Override
    public void updateMessages() {
        messagesAdapter.notifyDataSetChanged();
    }
}

package com.easemob.redpacket.widget;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.easemob.redpacket.R;
import com.easemob.redpacketsdk.constant.RPConstant;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.widget.chatrow.EaseChatRow;

public class ChatRowRedPacket extends EaseChatRow {

    private TextView mTvGreeting;
    private TextView mTvSponsorName;
    private TextView mTvPacketType;

    public ChatRowRedPacket(Context context, EMMessage message, int position, BaseAdapter adapter) {
        super(context, message, position, adapter);
    }

    @Override
    protected void onInflateView() {
        if (message.getBooleanAttribute(RPConstant.MESSAGE_ATTR_IS_RED_PACKET_MESSAGE, false)) {
            inflater.inflate(message.direct() == EMMessage.Direct.RECEIVE ?
                    R.layout.em_row_received_red_packet : R.layout.em_row_sent_red_packet, this);
        }
    }

    @Override
    protected void onFindViewById() {
        mTvGreeting = (TextView) findViewById(R.id.tv_money_greeting);
        mTvSponsorName = (TextView) findViewById(R.id.tv_sponsor_name);
        mTvPacketType = (TextView) findViewById(R.id.tv_packet_type);
    }

    @Override
    protected void onSetUpView() {
        String sponsorName = message.getStringAttribute(RPConstant.EXTRA_SPONSOR_NAME, "");
        String greetings = message.getStringAttribute(RPConstant.EXTRA_RED_PACKET_GREETING, "");
        mTvGreeting.setText(greetings);
        mTvSponsorName.setText(sponsorName);
        String packetType = message.getStringAttribute(RPConstant.MESSAGE_ATTR_RED_PACKET_TYPE, "");
        if (!TextUtils.isEmpty(packetType) && TextUtils.equals(packetType, RPConstant.GROUP_RED_PACKET_TYPE_EXCLUSIVE)) {
            mTvPacketType.setVisibility(VISIBLE);
            mTvPacketType.setText(R.string.exclusive_red_packet);
        } else {
            mTvPacketType.setVisibility(GONE);
        }
    }

    @Override
    protected void onBubbleClick() {
    }

    @Override
    protected void onViewUpdate(EMMessage msg) {
        switch (msg.status()) {
            case CREATE:
                onMessageCreate();
                break;
            case SUCCESS:
                onMessageSuccess();
                break;
            case FAIL:
                onMessageError();
                break;
            case INPROGRESS:
                onMessageInProgress( );
                break;
        }
    }

    protected void onMessageCreate() {
        progressBar.setVisibility(View.VISIBLE);
        statusView.setVisibility(View.GONE);
    }

    protected void onMessageSuccess() {
        progressBar.setVisibility(View.GONE);
        statusView.setVisibility(View.GONE);
    }

    protected void onMessageError() {
        progressBar.setVisibility(View.GONE);
        statusView.setVisibility(View.VISIBLE);
    }

    protected void onMessageInProgress() {
        progressBar.setVisibility(View.VISIBLE);
        statusView.setVisibility(View.GONE);
    }
}

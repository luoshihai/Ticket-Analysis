package cn.lsh.tick.presenter;

import android.app.Activity;

import cn.lsh.tick.api.DataManager;
import cn.lsh.tick.base.BasePresenter;
import cn.lsh.tick.bean.TicketRegular;
import cn.lsh.tick.manager.TicketRegularManager;
import cn.lsh.tick.presenter.view.IOpenResultView;

/**
 * @author xiaolong
 * @version v1.0
 * @function <描述功能>
 * @date: 2017/9/8 15:01
 */

public class OpenResultPresenter extends BasePresenter<IOpenResultView> {
    private TicketRegular mTicketRegular;

    public OpenResultPresenter(Activity activity) {
        super(activity);
    }

    public void getSingleOpenResult(String code, String issue) {
        addSubscribe(DataManager.getSinglePeroidCheck(code, issue).subscribe(getSubscriber(ticketOpenDataTicketInfo ->
                mView.getSingleOpenResultSuccess(ticketOpenDataTicketInfo.list.get(0))
        )));
    }

    public void getRegularCache(String code) {
        if (mTicketRegular != null) {
            mView.getRegularSuccess(mTicketRegular);
            return;
        }
        addSubscribe(TicketRegularManager.getTicketDataManager().getTicketRegularList().subscribe(getSubscriberNoProgress(ticketRegulars -> {
            for (TicketRegular ticketRegular : ticketRegulars) {
                if (ticketRegular.code.equals(code)) {
                    mTicketRegular = ticketRegular;
                    mView.getRegularSuccess(mTicketRegular);
                    break;
                }
            }
        })));
    }
}

package cn.lsh.tick.presenter.view;

import cn.lsh.tick.base.ILoadingView;
import cn.lsh.tick.bean.TicketOpenData;
import cn.lsh.tick.bean.TicketRegular;

/**
 * @author xiaolong
 * @version v1.0
 * @function <描述功能>
 * @date: 2017/9/8 15:02
 */

public interface IOpenResultView extends ILoadingView {
    void getSingleOpenResultSuccess(TicketOpenData ticketOpenData);

    void getRegularSuccess(TicketRegular ticketRegular);
}

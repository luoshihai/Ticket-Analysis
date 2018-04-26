package cn.lsh.tick.presenter.view;

import java.util.List;

import cn.lsh.tick.base.ILoadingView;
import cn.lsh.tick.bean.TicketType;

/**
 * @author xiaolong
 * @version v1.0
 * @function <描述功能>
 * @date: 2017/9/11 11:17
 */

public interface IFollowAddView extends ILoadingView {
    void onGetTicketListSuccess(List<TicketType> ticketTypeList);
}

package cn.lsh.tick.presenter;

import android.app.Activity;

import java.util.List;

import cn.lsh.tick.base.BasePresenter;
import cn.lsh.tick.bean.TicketRegular;
import cn.lsh.tick.presenter.view.INumberGenerateView;
import cn.lsh.tick.utils.NumberGenerateHelper;

/**
 * @author xiaolong
 * @version v1.0
 * @function <描述功能>
 * @date: 2017/09/11 11:10:50
 */

public class NumberGeneratePresenter extends BasePresenter<INumberGenerateView> {
    private NumberGenerateHelper numberGenerateHelper;

    public NumberGeneratePresenter(Activity activity, TicketRegular regular) {
        super(activity);
        numberGenerateHelper = new NumberGenerateHelper(regular);
    }

    public void generaterNumber(List<List<String>> numberBase) {
        addSubscribe(numberGenerateHelper.generateNumberGroup(numberBase).subscribe(getSubscriberNoProgress(t ->
                mView.onGenerateDataSuccess(t)
        )));
    }

}

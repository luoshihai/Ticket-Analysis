package cn.lsh.tick.presenter.view;

import cn.lsh.tick.base.ILoadingView;

/**
 * @author xiaolong
 * @version v1.0
 * @function <描述功能>
 * @date: 2017/9/11 11:17
 */

public interface INumberGenerateView extends ILoadingView {
    void onGenerateDataSuccess(String codeGroup);
}

package com.qzybj.libutil.app;

import android.app.Activity;
import java.util.Vector;

/**
 * 自定义activity管理栈<br>
 */
public class ActivityStack {
	private Vector<Activity> activityStack;
	private static ActivityStack instance;

	private ActivityStack() {}

	public static ActivityStack getInstance() {
		if (instance == null) {
			instance = new ActivityStack();
		}
		return instance;
	}

	public void removeActivity(Activity activity) {
		if (activity != null) {
			if (!activity.isFinishing()) {
				activity.finish();
			}
			activityStack.remove(activity);
			activity = null;
		}
	}
	
	public void removeAllActivityExcept(Class<?>... classes) {
		if (null == activityStack) {
			return;
		}

		for (int i = 0; i < activityStack.size(); i++) {
			Activity activity = activityStack.elementAt(i);
			if ((classes != null) && (classes.length > 0)) {
				boolean hasActiviy = false;
				for (Class<?> cls : classes) {
					if (cls.equals(activity.getClass())) {
						hasActiviy = true;
						break;
					}
				}
				if (hasActiviy) {
					continue;
				}
			}
			if (null != activity) {
				i--;
			}
			removeActivity(activity);
		}
	}
	public void addActivity(Activity activity) {
		if (activityStack == null) {
			activityStack = new Vector<Activity>();
		}
		activityStack.add(activity);
	}
	
	/**
	 * 获取栈项的activity
	 * @return Activity
	 */
	public Activity getTopActivity() {
		return activityStack.get(activityStack.size() - 1);
	}
}

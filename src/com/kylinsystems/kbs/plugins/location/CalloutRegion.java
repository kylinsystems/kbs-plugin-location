package com.kylinsystems.kbs.plugins.location;

import java.util.Properties;

import org.adempiere.base.IColumnCallout;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MRegion;
import org.compiere.util.Util;

public class CalloutRegion implements IColumnCallout {

	@Override
	public String start(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value, Object oldValue) {

		if(value==null || Util.isEmpty(value.toString()))
			return "";

		Integer C_Region_ID = (Integer)value;
		MRegion region = null;

		region = MRegion.get(ctx, C_Region_ID);
		mTab.setValue("RegionName", region.getName());

		return null;
	}

}

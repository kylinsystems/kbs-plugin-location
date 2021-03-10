package com.kylinsystems.kbs.plugins.location.factory;

import java.util.ArrayList;
import java.util.List;

import org.adempiere.base.IColumnCallout;
import org.adempiere.base.IColumnCalloutFactory;
import org.compiere.model.MLocation;

import com.kylinsystems.kbs.plugins.location.CalloutCity;
import com.kylinsystems.kbs.plugins.location.CalloutRegion;

public class ColumnCalloutFactory implements IColumnCalloutFactory {

	@Override
	public IColumnCallout[] getColumnCallouts(String tableName, String columnName) {

		List<IColumnCallout> list = new ArrayList<IColumnCallout>();

		if (tableName.equals(MLocation.Table_Name)) {

			if (columnName.equals(MLocation.COLUMNNAME_C_Region_ID))
				list.add(new CalloutRegion());
			else if (columnName.equals(MLocation.COLUMNNAME_C_City_ID))
				list.add(new CalloutCity());

		}

		return list != null ? list.toArray(new IColumnCallout[0]) : new IColumnCallout[0];
	}

}

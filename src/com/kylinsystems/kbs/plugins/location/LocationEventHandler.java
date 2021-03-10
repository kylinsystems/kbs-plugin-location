package com.kylinsystems.kbs.plugins.location;

import org.adempiere.base.event.AbstractEventHandler;
import org.adempiere.base.event.IEventTopics;
import org.compiere.model.MLocation;
import org.compiere.model.PO;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Util;
import org.osgi.service.event.Event;

public class LocationEventHandler extends AbstractEventHandler {
	
	private static CLogger log = CLogger.getCLogger(LocationEventHandler.class);
	

	@Override
	protected void initialize() {
		registerTableEvent(IEventTopics.PO_AFTER_NEW, MLocation.Table_Name);
		registerTableEvent(IEventTopics.PO_AFTER_CHANGE, MLocation.Table_Name);
	}


	@Override
	protected void doHandleEvent(Event event) {
		if (event.getTopic().equals(IEventTopics.PO_AFTER_NEW)) {
			PO po = getPO(event);
			if (po instanceof MLocation) {
				MLocation location = (MLocation)po;
				updateLabel(location);
			}
		} else if (event.getTopic().equals(IEventTopics.PO_AFTER_CHANGE)) {
			PO po = getPO(event);
			if (po instanceof MLocation) {
				MLocation location = (MLocation)po;
				updateLabel(location);
			}
		}
	}

	
	private void updateLabel(MLocation location) {
		String label; // Label Format : C_Location_ID + "-" + City + Address1/2

		if (!Util.isEmpty(location.getCity()) && !Util.isEmpty(location.getAddress1()) && !Util.isEmpty(location.getAddress2())) {
			label = location.getCity() + "," + location.getAddress1() + "," + location.getAddress2();
		} else if (!Util.isEmpty(location.getCity()) && !Util.isEmpty(location.getAddress1())) {
			label = location.getCity() + "," + location.getAddress1();
		} else if (!Util.isEmpty(location.getCity())) {
			label = location.getCity();
		} else {
			label = "";
		}
		
		label = location.getC_Location_ID() + "-" + label;
		location.setComments(label);
		
		String sql = "UPDATE C_Location l" + " SET Comments='" + location.getComments() + "'"
				+ " WHERE C_Location_ID = ?";
		int no = DB.executeUpdate(sql, new Object[] { Integer.valueOf(location.getC_Location_ID()) }, false,
				location.get_TrxName(), 0);
		if (no != 1)
			log.warning("(1) #" + no);
	}

}

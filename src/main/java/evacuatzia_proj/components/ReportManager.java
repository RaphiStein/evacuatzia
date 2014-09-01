package evacuatzia_proj.components;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.vividsolutions.jts.geom.Coordinate;

import evacuatzia_proj.exceptions.EvacuatziaException;
import evacuatzia_proj.sqlhelpers.SessionFactoryUtil;

public class ReportManager extends LocationBasedItemManager {
	private static final SessionFactory sf = SessionFactoryUtil.getSessionFactory();
	
	public static Report editReport(Report report, String title, Geometry location, Date reportTime) {
		// TODO: validate all "must have" fields
		Session s = sf.openSession();
		Transaction t = s.beginTransaction();
		Long reportId = report.getEventID();
		try {
			Criteria cr = s.createCriteria(evacuatzia_proj.sqlhelpers.beans.Report.class);
			cr.add(Restrictions.eq("id" ,reportId));
			evacuatzia_proj.sqlhelpers.beans.Report dbReport = (evacuatzia_proj.sqlhelpers.beans.Report) cr.uniqueResult();
			if (null == dbReport) {
				// report apparently was deleted...
				// TODO: throw something
			}
			dbReport.setTitle(title);
			// TODO: set coordinates or change to setting Geometry directly (i preffer the latter)
			dbReport.setTime(reportTime);
			s.update(dbReport);
			t.commit();
		} catch (RuntimeException e) {
			t.rollback();
			throw e;
		} finally {
			s.close();
		}
		return new Report(report.getEventID(), title, location, reportTime, report.getUser());
	}

	public static Report createNewReport(User user, String title, Geometry location, Date reportTime) {
		Session s = sf.openSession();
		Transaction t = s.beginTransaction();
		try {
			
		} catch (RuntimeException e) {
			t.rollback();
		} finally {
			s.close();
		}
		
		// TODO: implement
		return null;
	}
	
	public static boolean removeReport(User user, Report report) {
		Session s = sf.openSession();
		Transaction t = s.beginTransaction();
		try {
			
		} catch (RuntimeException e) {
			t.rollback();
		} finally {
			s.close();
		}
		
		// TODO: implement
		return false;		
	}
	
	@SuppressWarnings("unchecked")
	public static List<Report> getReportsByUser(User user) {
		if (null == user) {
			throw new EvacuatziaException("user must not be null");
		}
		Long id = user.getId();
		List<evacuatzia_proj.sqlhelpers.beans.Report> dbReportList;
		Session s = sf.openSession();
		Transaction t = s.beginTransaction();
		try {
			Criteria cr = s.createCriteria(evacuatzia_proj.sqlhelpers.beans.Report.class);
			cr.add(Restrictions.eq("userReported" ,id));
			dbReportList = cr.list();
			t.commit();
		} catch (RuntimeException e) {
			t.rollback();
			throw e;
		} finally {
			s.close();
		}
		List<Report> retReportList = new ArrayList<>();
		for (evacuatzia_proj.sqlhelpers.beans.Report dbReport: dbReportList) {
			Coordinate coor = dbReport.getLocation().getCoordinate();
			Geometry geom = new Geometry(coor.x, coor.y, dbReport.getRadius());
			retReportList.add(new Report(dbReport.getId(), dbReport.getTitle(), geom, dbReport.getTime(), user));
		}
		return retReportList;
	}
}

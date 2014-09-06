package evacuatzia_proj.components;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Polygon;

import evacuatzia_proj.components.helpers.CompUtils;
import evacuatzia_proj.components.helpers.OurPoint;
import evacuatzia_proj.exceptions.EvacuatziaException;
import evacuatzia_proj.exceptions.MissingInDatabaseException;
import evacuatzia_proj.sqlhelpers.SessionFactoryUtil;
import evacuatzia_proj.sqlhelpers.beans.UserInfo;
import evacuatzia_proj.sqlhelpers.common.Utils;

public class ReportManager extends LocationBasedItemManager {
	private static final SessionFactory sf = SessionFactoryUtil.getSessionFactory();

	public static Report editReport(Report report, String title, Geometry location, Date reportTime) {
		if (null == report) {
			throw new EvacuatziaException("report must not be null");
		}
		// TODO: check/validate all "must have" fields
		Session s = sf.openSession();
		Transaction t = s.beginTransaction();
		Long reportId = report.getEventID();
		try {
			Criteria cr = s.createCriteria(evacuatzia_proj.sqlhelpers.beans.Report.class);
			cr.add(Restrictions.eq("id", reportId));
			evacuatzia_proj.sqlhelpers.beans.Report dbReport = (evacuatzia_proj.sqlhelpers.beans.Report) cr
					.uniqueResult();
			if (null == dbReport) {
				// report apparently was deleted...
				throw new MissingInDatabaseException("Report is missing from database. Was it removed?");
			}
			dbReport.setTitle(title);
			// TODO: set coordinates or change to setting Geometry directly (i
			// preffer the latter)
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
		evacuatzia_proj.sqlhelpers.beans.Report dbReport;
		UserInfo dbUser;
		Session s = sf.openSession();
		Transaction t = s.beginTransaction();
		try {
			dbUser = getUserInfoByApiUser(user, s);
			if (null == dbUser) {
				throw new MissingInDatabaseException("User is missing from database. Were he/she removed?");
			}
			dbReport = new evacuatzia_proj.sqlhelpers.beans.Report(dbUser,
					title, location.getLongitude(), location.getLatitude(), location.getRadius(), reportTime);
			s.save(dbReport);
			t.commit();
		} catch (RuntimeException e) {
			t.rollback();
			throw e;
		} finally {
			s.close();
		}
		return createApiReportFromDbReportAndDbUser(dbReport, dbUser);
	}

	public static void removeReport(Report report) {
		if (null == report) {
			throw new EvacuatziaException("report must not be null");
		}
		Long id = report.getEventID();
		evacuatzia_proj.sqlhelpers.beans.Report dbReport;
		Session s = sf.openSession();
		Transaction t = s.beginTransaction();
		try {
			Criteria cr = s.createCriteria(evacuatzia_proj.sqlhelpers.beans.Report.class);
			cr.add(Restrictions.eq("id", id));
			dbReport = (evacuatzia_proj.sqlhelpers.beans.Report) cr.uniqueResult();
			s.delete(dbReport);
			t.commit();
		} catch (RuntimeException e) {
			t.rollback();
			throw e;
		} finally {
			s.close();
		}
	}

	@SuppressWarnings("unchecked")
	public static List<Report> getReportsByUser(User user) {
		if (null == user) {
			throw new EvacuatziaException("user must not be null");
		}
		List<evacuatzia_proj.sqlhelpers.beans.Report> dbReportList;
		Session s = sf.openSession();
		Transaction t = s.beginTransaction();
		try {
			UserInfo dbUser = UserManager.getUserInfoByUsername(user.getUsername(), s);
			dbReportList = getDbReportsByDbUser(dbUser, s);
			t.commit();
		} catch (RuntimeException e) {
			t.rollback();
			throw e;
		} finally {
			s.close();
		}
		return createApiReportListFromDbReportListAndUser(user, dbReportList);
	}

	public static List<Report> getAllReports(){
		List<Report> resReports;
		Session s = sf.openSession();
		Transaction t = s.beginTransaction();
		try {
			Criteria cr = s.createCriteria(evacuatzia_proj.sqlhelpers.beans.Report.class);
			List<evacuatzia_proj.sqlhelpers.beans.Report> dbReportList = cr.list();
			resReports = createApiReportListFromDbReportList(dbReportList);
			t.commit();
		} catch (RuntimeException e) {
			t.rollback();
			throw e;
		} finally {
			s.close();
		}
		return resReports;
	}
	
	public static List<Report> getReportsByLocation(double lat, double lon, int radius){
		// TODO:
		// use getAllReportsInRectangle with two opposing points or getAllReportsInPoly with a list of points instead
		// there is java script code to create a the points out of loat lon and radius
		return null;
	}
	
	public static List<Report> getReportsByTitle(String title){
		// TODO - answer: do we realy want this? maybe we can do with the function for partial match?
		CommonUtils.validateTitleSupplied(title);
		List<Report> resReports;
		Session s = sf.openSession();
		Transaction t = s.beginTransaction();
		try {
			List<evacuatzia_proj.sqlhelpers.beans.Report> dbReportList = getDbReportsByTitle(title, s);
			resReports = createApiReportListFromDbReportList(dbReportList);
			t.commit();
		} catch (RuntimeException e) {
			t.rollback();
			throw e;
		} finally {
			s.close();
		}
		return resReports;
	}
	
	public static List<Report> getReportsByPartialTitle(String partialTitle){
		// TODO (tomorrow)
		return null;
		
	}
	private static List<Report> createApiReportListFromDbReportListAndUser(User user,
			List<evacuatzia_proj.sqlhelpers.beans.Report> dbReportList) {
		List<Report> retReportList = new ArrayList<>();
		for (evacuatzia_proj.sqlhelpers.beans.Report dbReport : dbReportList) {
			Coordinate coor = dbReport.getLocation().getCoordinate();
			Geometry geom = new Geometry(coor.x, coor.y, dbReport.getRadius());
			retReportList.add(new Report(dbReport.getId(), dbReport.getTitle(), geom, dbReport.getTime(), user));
		}
		return retReportList;
	}
	
	public static List<Report> getAllReportsInRectangle(OurPoint p1, OurPoint p2) {
		// create rectangle polygon
		Polygon rect = CompUtils.createRectangleFromTwoPoints(p1, p2);
		return reportsInPolygon(rect);
	}
	
	
	/**
	 * @param points
	 * 			list of points that form a polygon - first and last point shouldn't
	 * 			be the same (we'll close the polygon ourselves)
	 * @return
	 * 			A list of all the reports of which the center falls within that polygon
	 */
	public static List<Report> getAllReportsInPoly(List<OurPoint> points) {
		Polygon poly = CompUtils.createPolygonFromOurPointList(points);
		return reportsInPolygon(poly);
	}
	
	private static List<Report> reportsInPolygon(Polygon poly) {
		Session s = sf.openSession();
		Transaction t = s.beginTransaction();
		String hql = "select r from Report r where within(r.location, :filter) = true";
		Query q = s.createQuery(hql);
		q.setParameter("filter", poly);
		List<evacuatzia_proj.sqlhelpers.beans.Report> dbReportList = q.list(); 
		List<Report> resReports = createApiReportListFromDbReportList(dbReportList);
		t.commit();
		s.close();
		return resReports;
	}

	// Package protected
	static void removeReportsByDbUser(UserInfo user, Session s) {
		List<evacuatzia_proj.sqlhelpers.beans.Report> userReportList = ReportManager.getDbReportsByDbUser(user, s);
		for(evacuatzia_proj.sqlhelpers.beans.Report dbReport: userReportList) {
			s.delete(dbReport);
		}
	}
	
	private static List<evacuatzia_proj.sqlhelpers.beans.Report> getDbReportsByDbUser(UserInfo user, Session s) {
		List<evacuatzia_proj.sqlhelpers.beans.Report> dbReportList;
		Criteria cr = s.createCriteria(evacuatzia_proj.sqlhelpers.beans.Report.class);
		cr.add(Restrictions.eq("userReported", user));
		dbReportList = cr.list();
		return dbReportList;
	}
	
	private static List<evacuatzia_proj.sqlhelpers.beans.Report> getDbReportsByTitle(String title, Session s) {
		List<evacuatzia_proj.sqlhelpers.beans.Report> dbReportList;
		Criteria cr = s.createCriteria(evacuatzia_proj.sqlhelpers.beans.Report.class);
		cr.add(Restrictions.eq("title", title));
		dbReportList = cr.list();
		return dbReportList;
	}

	private static List<Report> createApiReportListFromDbReportList(
			List<evacuatzia_proj.sqlhelpers.beans.Report> dbReportList) {
		List<Report> reports = new ArrayList<>();
		for(evacuatzia_proj.sqlhelpers.beans.Report dbReport: dbReportList) {
			UserInfo dbUser = dbReport.getUserReported();
			reports.add(createApiReportFromDbReportAndDbUser(dbReport, dbUser));
		}
		return reports;
	}

	private static Report createApiReportFromDbReportAndDbUser(evacuatzia_proj.sqlhelpers.beans.Report dbReport, UserInfo dbUser) {
		Geometry geom = Utils.createOurGeometryFromJtsAndRadius(dbReport.getLocation(), dbReport.getRadius());
		return new Report(dbReport.getId(), dbReport.getTitle(), geom, dbReport.getTime(), UserManager.createApiUserFromDbUser(dbUser));
	}

	private static UserInfo getUserInfoByApiUser(User user, Session s) {
		Criteria cr = s.createCriteria(UserInfo.class);
		cr.add(Restrictions.eq("id", user.getId()));
		return (UserInfo) cr.uniqueResult();
	}
}

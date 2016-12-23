package com.dfn.alerts.dataaccess.dao.impl;

import com.dfn.alerts.beans.SearchObject;
import com.dfn.alerts.beans.financial.FinancialLineItem;
import com.dfn.alerts.constants.IConstants;
import com.dfn.alerts.dataaccess.orm.impl.BusinessActivityDesc;
import com.dfn.alerts.dataaccess.orm.impl.FinancialSegment;
import com.dfn.alerts.dataaccess.orm.impl.ProductDescription;
import com.dfn.alerts.dataaccess.dao.FinancialSegmentDAO;
import com.dfn.alerts.dataaccess.utils.DBUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.*;

import javax.sql.DataSource;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Priyabashitha L.P.
 * Date: 4/22/2015
 * Time: 12:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class FinancialSegmentDAOImpl implements FinancialSegmentDAO {

    private SessionFactory sessionFactory = null;
    private static final Logger LOG = LogManager.getLogger(FinancialSegmentDAOImpl.class);
    private static final String FINANCIAL_SEGMENT_LOG_PRE_FIX = " :: Financial Segmentation :: ";

    enum mappings {
        COMPANY_ID("segmentKey.companyId", "companyId"),
        YEAR("segmentKey.year", "year"),
        PERIOD("segmentKey.period", "period"),
        INFO_TYPE("segmentKey.infoType", null),
        FIELD_ID("segmentKey.fieldId", "fieldId"),
        SEGMENT_TYPE("segmentKey.segmentTypeId", null),
        SEGMENT_ID("segmentKey.segmentId", "segmentId"),
        FIELD_NAME("fieldNameEn", "fieldNameEn"),
        SEGMENT_CODE("segmentCode", null),
        CHILD_SEGMENT_CODE("childSegmentCode", null),
        PARENT_SEGMENT_CODE("parentSegmentCode", null),
        STANDARD_SEGMENT_CODE("standardSegmentCode", null),
        COMPANY_NAME("companyNameEn","companyNameEn"),
        SEGMENT_DESC("segmentDesc", "segmentDesc"),
        SEGMENT_TYPE_DESC("segmentTypeDesc", "segmentTypeId"),
        FULL_PERIOD("fullPeriod", "fullPeriod"),
        PERCENTAGE("percentage", "percentage");

        private String hibernateMapping;
        private String jsMapping;

        static Map<String, String> map = new HashMap<String, String>(16);

        static {
            for(mappings mapping : mappings.values()){
                if(mapping.jsMapping != null) {
                    map.put(mapping.jsMapping, mapping.hibernateMapping);
                }
            }
        }

        mappings(String hibernateMapping, String jsMapping) {
            this.hibernateMapping = hibernateMapping;
            this.jsMapping = jsMapping;
        }

        public static String getHibernateMapping(String jsMapping) {
            return map.get(jsMapping);
        }
    }

    List<String> sortFields = new ArrayList<String>(
            Arrays.asList(mappings.COMPANY_NAME.hibernateMapping, mappings.SEGMENT_TYPE_DESC.hibernateMapping,
                    mappings.SEGMENT_DESC.hibernateMapping, mappings.FULL_PERIOD.hibernateMapping,
                    mappings.FIELD_NAME.hibernateMapping, mappings.PERCENTAGE.hibernateMapping));


    @Override
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void setDriverManagerDataSource(DataSource driverManagerDataSource) {
    }

    @Override
    public List<FinancialSegment> getSegmentDataByCompany(int companyId) {
        List<FinancialSegment> financialSegments = null;
        Session session = null;
        try {
            session = this.sessionFactory.openSession();
            Criteria criteria = session.createCriteria(FinancialSegment.class);
            criteria.add(Restrictions.eq(mappings.COMPANY_ID.hibernateMapping, companyId));
            financialSegments = criteria.list();
        } catch (Exception e) {
            LOG.error(FINANCIAL_SEGMENT_LOG_PRE_FIX + " Loading segment data by company id ", e);
        }finally {
            if (session != null) {
                session.close();
            }
        }
        return financialSegments;
    }

    @Override
    public Map<String, List<FinancialLineItem>> getFinancialLineItems() {
        Map<String, List<FinancialLineItem>> financialLineItem = null;
        Session session = null;
        try {
            session = this.sessionFactory.openSession();
            Criteria criteria = session.createCriteria(FinancialSegment.class);
            ProjectionList projectionList = Projections.projectionList();
            projectionList.add(Projections.distinct(Projections.property(mappings.FIELD_ID.hibernateMapping)));
            projectionList.add(Projections.property(mappings.INFO_TYPE.hibernateMapping));
            projectionList.add(Projections.property(mappings.FIELD_NAME.hibernateMapping));
            criteria.setProjection(projectionList);
            List segmentList = criteria.list();
            if (segmentList != null) {
                financialLineItem = DBUtils.generateFinancialLineItemMap(segmentList);
            }

        } catch (Exception e) {
            LOG.error(FINANCIAL_SEGMENT_LOG_PRE_FIX + " Loading financial line items ", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return financialLineItem;
    }

    @Override
    public List<Object> getFinancialSegments() {
        List<Object>  segmentData = null;
        Session session = null;
        /**
         * segment_type_id description
         * 5 product
         * 6 business
         */
        Set<Integer> segmentTypes = new HashSet<Integer>(Arrays.asList(5, 6));
        try {
            session = this.sessionFactory.openSession();
            Criteria criteria = session.createCriteria(FinancialSegment.class);
            criteria.add(Restrictions.in(mappings.SEGMENT_TYPE.hibernateMapping, segmentTypes));
            criteria.addOrder(Order.desc((mappings.SEGMENT_TYPE.hibernateMapping)));
            ProjectionList projectionList = Projections.projectionList();
            projectionList.add(Projections.property(mappings.SEGMENT_TYPE.hibernateMapping));
            projectionList.add(Projections.property(mappings.SEGMENT_CODE.hibernateMapping));
            projectionList.add(Projections.property(mappings.STANDARD_SEGMENT_CODE.hibernateMapping));
            criteria.setProjection(Projections.distinct(projectionList));
            segmentData = criteria.list();

        } catch (Exception e) {
            LOG.error(FINANCIAL_SEGMENT_LOG_PRE_FIX + " Loading financial business activities ", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return segmentData;
    }

    @Override
    public Set<String> getFinancialPeriods() {
        Set<String> financialPeriods = null;
        Session session = null;
        try {
            session = this.sessionFactory.openSession();
            Criteria criteria = session.createCriteria(FinancialSegment.class);
            criteria.addOrder(Order.desc((mappings.YEAR.hibernateMapping)));
            ProjectionList projectionList = Projections.projectionList();
            projectionList.add(Projections.groupProperty(mappings.YEAR.hibernateMapping));
            projectionList.add(Projections.groupProperty(mappings.PERIOD.hibernateMapping));
            criteria.setProjection(projectionList);
            List periods =criteria.list();
            if(periods!=null){
                financialPeriods = new TreeSet<String>(Collections.reverseOrder());
                for(Object o : periods) {
                    Object[] row = (Object[]) o;
                    financialPeriods.add(row[0]+""+row[1]);
                }
            }

        } catch (Exception e) {
            LOG.error(FINANCIAL_SEGMENT_LOG_PRE_FIX + " Loading financial line items ", e);
        }finally {
            if (session != null) {
                session.close();
            }
        }
        return financialPeriods;
    }

    @Override
    public Map<String, String> getBusinessActivityDescription() {
        Map<String, String> businessActivityDesc = null;
        Session session = null;
        try {
            session = this.sessionFactory.openSession();
            Criteria criteria = session.createCriteria(BusinessActivityDesc.class);
            ProjectionList projectionList = Projections.projectionList();
            projectionList.add(Projections.distinct(Projections.property("activityId")));
            projectionList.add(Projections.property("descriptionEN"));
            criteria.setProjection(projectionList);
            List segmentList =criteria.list();
            if(segmentList!=null){
                businessActivityDesc = new HashMap<String, String>();
                for(Object o : segmentList) {
                    Object[] row = (Object[]) o;
                    businessActivityDesc.put(row[0].toString(), row[1].toString());
                }
            }
        } catch (Exception e) {
            LOG.error(FINANCIAL_SEGMENT_LOG_PRE_FIX + " Loading business activity descriptions ", e);
        }finally {
            if (session != null) {
                session.close();
            }
        }
        return businessActivityDesc;
    }

    @Override
    public Map<String, String> getProductDescription() {
        Map<String, String> productActivityDesc = null;
        Session session = null;
        try {
            session = this.sessionFactory.openSession();
            Criteria criteria = session.createCriteria(ProductDescription.class);
            ProjectionList projectionList = Projections.projectionList();
            projectionList.add(Projections.distinct(Projections.property("productId")));
            projectionList.add(Projections.property("descriptionEN"));
            criteria.setProjection(projectionList);
            List segmentList =criteria.list();
            if(segmentList!=null){
                productActivityDesc = new HashMap<String, String>();
                for(Object o : segmentList) {
                    Object[] row = (Object[]) o;
                    productActivityDesc.put(row[0].toString(), row[1].toString());
                }
            }
        } catch (Exception e) {
            LOG.error(FINANCIAL_SEGMENT_LOG_PRE_FIX + " Loading gics activity descriptions ", e);
        }finally {
            if (session != null) {
                session.close();
            }
        }
        return productActivityDesc;
    }

    @Override
    public SearchObject screenFinancialSegmentation(Map<String, Object> filterMap) {
        SearchObject searchObject = null;
        Long totalCount = 0l;
        Session session = null;
        Criteria criteria;
        try {
            session = this.sessionFactory.openSession();
            Conjunction filters = generateFilters(filterMap);
            int pageIndex = Integer.parseInt(filterMap.get(IConstants.FinancialSegmentScreener.PAGE_INDEX).toString());
            int pageSize = Integer.parseInt(filterMap.get(IConstants.FinancialSegmentScreener.PAGE_SIZE).toString());
            if(pageIndex == 0) {
                criteria = session.createCriteria(FinancialSegment.class);
                criteria.add(filters);
                totalCount = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
            }
            criteria = session.createCriteria(FinancialSegment.class);
            criteria.add(filters);
            criteria = addPagination(criteria, pageIndex, pageSize);
            criteria = addSorting(criteria, filterMap);
            List<FinancialSegment> segmentList = criteria.list();
            if(segmentList == null){
                searchObject = new SearchObject(0, Collections.emptyList());
            }else {
                searchObject = new SearchObject(totalCount.intValue(), segmentList);
            }
        } catch (Exception ex) {
            LOG.error(FINANCIAL_SEGMENT_LOG_PRE_FIX + " screening financial segmentation ", ex);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return searchObject;
    }

    private Conjunction generateFilters(Map<String, Object> filterMap) {
        Conjunction conjunction = Restrictions.conjunction();

        Criterion fieldFilter = generateFieldFilter(filterMap);
        if (fieldFilter != null) {
            conjunction.add(fieldFilter);
        }

        Criterion productFilter = generateSegmentFilter(filterMap, IConstants.FinancialSegmentScreener.PRODUCT_TYPE,
                IConstants.FinancialSegmentScreener.PRODUCT_LIST, IConstants.FinancialSegmentType.PRODUCT);
        if (productFilter != null) {
            conjunction.add(productFilter);
        }

        Criterion businessActivityFilter = generateSegmentFilter(filterMap, IConstants.FinancialSegmentScreener.BUSINESS_ACTIVITY_TYPE,
                IConstants.FinancialSegmentScreener.BUSINESS_ACTIVITY_LIST, IConstants.FinancialSegmentType.BUSINESS_ACTIVITY);
        if (businessActivityFilter != null) {
            conjunction.add(businessActivityFilter);
        }

        Criterion countryFilter = generateCountryFilter(filterMap);
        if (countryFilter != null) {
            conjunction.add(countryFilter);
        }

        Criterion periodFilter = generatePeriodFilter(filterMap);
        if (periodFilter != null) {
            conjunction.add(periodFilter);
        }

        Criterion segmentIdFilter = generateSegmentIdFilter(filterMap);
        if(segmentIdFilter != null) {
            conjunction.add(segmentIdFilter);
        }
        conjunction.add(generateTotalFilter());
        return conjunction;
    }

    private Criterion generateTotalFilter() {
        return Restrictions.and(Restrictions.ne(mappings.SEGMENT_ID.hibernateMapping, 0), Restrictions.ne(mappings.SEGMENT_TYPE.hibernateMapping, 0),
                Restrictions.isNotNull(mappings.PERCENTAGE.hibernateMapping));//USTRY340  see comments PD team asked to remove percentage null set from results
    }

    private Criterion generateFieldFilter(Map<String, Object> filterMap) {
        Criterion criterion = null;
        if (filterMap.containsKey(IConstants.FinancialSegmentScreener.FIELD_LIST)) {
            Integer[] fieldArray = (Integer[]) filterMap.get(IConstants.FinancialSegmentScreener.FIELD_LIST);
            criterion = Restrictions.in(mappings.FIELD_ID.hibernateMapping, Arrays.asList(fieldArray));
        }
        return criterion;
    }

    private Criterion generateSegmentFilter(Map<String, Object> filterMap, String keyType, String keyData, IConstants.FinancialSegmentType segmentType) {
        Criterion criterion = null;
        if (filterMap.containsKey(keyType) && filterMap.containsKey(keyData)) {
            int activityType = Integer.parseInt(filterMap.get(keyType).toString());
            String[] activityList = (String[]) filterMap.get(keyData);
            if (activityList.length > 0) {
                String property = mappings.SEGMENT_CODE.hibernateMapping;
                if (activityType == IConstants.SegmentStandardType.STANDARD.getSegmentType()) {
                    property = mappings.STANDARD_SEGMENT_CODE.hibernateMapping;
                }
                criterion = Restrictions.and(Restrictions.eq(mappings.SEGMENT_TYPE.hibernateMapping, segmentType.getSegmentType()),
                        generateDisjunctionFilter(activityList, property));
            }
        }
        return criterion;
    }

    private Criterion generateDisjunctionFilter(String[] dataList, String property) {
        Disjunction disjunction = Restrictions.disjunction();
        for (String data : dataList) {
            disjunction.add(Restrictions.like(property, "%," + data + ",%"));
        }
        return disjunction;
    }

    private Criterion generateCountryFilter(Map<String, Object> filterMap) {
        Criterion criterion = null;
        if (filterMap.containsKey(IConstants.FinancialSegmentScreener.COUNTRY_LIST)) {
            String[] countryList = (String[]) filterMap.get(IConstants.FinancialSegmentScreener.COUNTRY_LIST);
            if (countryList.length > 0) {
                Criterion countryCriterion = Restrictions.and(Restrictions.eq(mappings.SEGMENT_TYPE.hibernateMapping, IConstants.FinancialSegmentType.COUNTRY.getSegmentType()),
                        generateDisjunctionFilter(countryList, mappings.SEGMENT_CODE.hibernateMapping));

                Criterion regionCriterion = Restrictions.and(Restrictions.eq(mappings.SEGMENT_TYPE.hibernateMapping, IConstants.FinancialSegmentType.REGION.getSegmentType()),
                        generateDisjunctionFilter(countryList, mappings.CHILD_SEGMENT_CODE.hibernateMapping));

                Criterion zoneCity = Restrictions.or(Restrictions.eq(mappings.SEGMENT_TYPE.hibernateMapping, IConstants.FinancialSegmentType.ZONE.getSegmentType()),
                        Restrictions.eq(mappings.SEGMENT_TYPE.hibernateMapping, IConstants.FinancialSegmentType.CITY.getSegmentType()));
                Criterion zoneCityCriterion = Restrictions.and(zoneCity, generateDisjunctionFilter(countryList, mappings.PARENT_SEGMENT_CODE.hibernateMapping));

                criterion = Restrictions.or(countryCriterion, regionCriterion, zoneCityCriterion);
            }
        }
        return criterion;
    }

    private Criterion generatePeriodFilter(Map<String, Object> filterMap) {
        Criterion criterion = null;
        if (filterMap.containsKey(IConstants.FinancialSegmentScreener.PERIOD_TYPE)) {
            int periodType = Integer.parseInt(filterMap.get(IConstants.FinancialSegmentScreener.PERIOD_TYPE).toString());
            if (periodType == IConstants.SegmentPeriodType.ANNUAL.getPeriodType() &&
                    filterMap.containsKey(IConstants.FinancialSegmentScreener.PERIOD)) {
                int period = Integer.parseInt(filterMap.get(IConstants.FinancialSegmentScreener.PERIOD).toString());
                criterion = Restrictions.eq(mappings.PERIOD.hibernateMapping, period);
            } else if (periodType == IConstants.SegmentPeriodType.PERIOD.getPeriodType() &&
                    filterMap.containsKey(IConstants.FinancialSegmentScreener.PERIOD) &&
                    filterMap.containsKey(IConstants.FinancialSegmentScreener.YEAR)) {
                int period = Integer.parseInt(filterMap.get(IConstants.FinancialSegmentScreener.PERIOD).toString());
                int year = Integer.parseInt(filterMap.get(IConstants.FinancialSegmentScreener.YEAR).toString());
                criterion = Restrictions.and(Restrictions.eq(mappings.YEAR.hibernateMapping, year), Restrictions.eq(mappings.PERIOD.hibernateMapping, period));
            }
        }
        return criterion;
    }

    private Criterion generateSegmentIdFilter(Map<String, Object> filterMap) {
        Criterion criterion = null;
        if(filterMap.containsKey(IConstants.FinancialSegmentScreener.SEGMENT_ID)) {
            int segmentId = Integer.parseInt(filterMap.get(IConstants.FinancialSegmentScreener.SEGMENT_ID).toString());
            criterion = Restrictions.eq(mappings.SEGMENT_ID.hibernateMapping,segmentId);
        }
        return criterion;
    }

    private Criteria addPagination(Criteria criteria, int pageIndex, int pageSize) {
        criteria.setFirstResult(pageIndex * (pageSize - 1));
        criteria.setMaxResults(pageSize);
        return criteria;
    }

    private Criteria addSorting(Criteria criteria, Map<String, Object> filterMap) {
        String sortOrder = filterMap.get(IConstants.FinancialSegmentScreener.SORT_ORDER).toString();
        String sortField = filterMap.get(IConstants.FinancialSegmentScreener.SORT_FIELD).toString();

        String hibernateSortField = mappings.getHibernateMapping(sortField);
        if(hibernateSortField != null){
            criteria.addOrder(IConstants.DEFAULT_SORT_ORDER_DESC.equalsIgnoreCase(sortOrder) ? Order.desc(hibernateSortField) : Order.asc(hibernateSortField));
        }

        for (String key : sortFields) {
            if(!key.equalsIgnoreCase(hibernateSortField)) {
                criteria.addOrder(Order.desc(key));
            }
        }

        return criteria;
    }
}

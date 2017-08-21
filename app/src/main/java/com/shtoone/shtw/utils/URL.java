package com.shtoone.shtw.utils;

import android.text.TextUtils;
import android.util.Log;

import com.socks.library.KLog;

public class URL {

    public static final String TAG = "URL";

    /**
     * 常用接口、url
     */
    private URL() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 更新地址
     */
    public static final String UpdateURL = "http://120.27.146.66:8083/nxsy/appVersion/version.xml";


    /**
     * 基础地址
     */
//    public static final String BaseURL = "http://120.27.146.66:8083/nxsy/";
//    public static final String BaseURL = "http://192.168.11.113:8081/qms/";
//    public static final String BaseURL = "http://121.40.150.65:8083/zgjjqms/";
//    public static final String BaseURL = "http://120.26.127.135:8084/zgjj/";
//    public static final String BaseURL = "http://120.26.127.135:8083/xztl/";

    //中国交建平台地址
//    public static final String BaseURL = "http://121.40.163.88:8081/zjlq/";

    //中交集团二航局地址
//    public static final String BaseURL = "http://120.26.127.135:8082/zj2hj/";

    public static final String BaseURL = "http://192.168.11.100:8080/jeecg/";

    /**
     * 登录地址
     */
    public static final String Login_URL = BaseURL + "app.do?AppLogin&userName=%1&userPwd=%2&OSType=2";

    /**
     * 登录验证
     *
     * @param username 用户名
     * @param password 密码
     * @return 返回拼凑后的url
     */
    public static String loginCheck(String username, String password) {
        String url = Login_URL.replace("%1", username).replace("%2", password);
        KLog.e(TAG, "登录验证 :" + url);
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        Log.e("登录url","登录url=:"+url);
        return url;
    }

    /**
     * 组织结构面板
     */
    public static final String ORGANIZATION = BaseURL + "app.do?AppDepartTree&updateDepartTime=%1&funtype=%2&userGroupId=%3&type=%4";

    /**
     * 组织结构面板
     *
     * @param dateTime    时间
     * @param type        部门类型（试验室或者拌合站）
     * @param userGroupID 组织ID
     * @param userRole    组织ID
     * @return 返回拼凑后的url
     */
    public static String getOrganizationData(String dateTime, String type, String userGroupID, String userRole) {
        dateTime = DateUtils.ChangeTimeToLong(dateTime);
        //如果开始时间大于结束时间，返回null
        String url = ORGANIZATION.replace("%1", dateTime).replace("%2", type).replace("%3", userGroupID).replace("%4", userRole);
        KLog.e(TAG, "组织结构URL :" + url);
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        return url;
    }


    /**
     * 主界面地址 TODO
     */
    public static final String Main_URL = BaseURL + "app.do?AppHntMain&departId=%1";

    /**
     * 拌合站设备列表
     */
    public static final String COMM_DEVICE = BaseURL + "app.do?getShebeiList&userGroupId=%1";

    /**
     * 拌合站设备列表
     *
     * @param userGroupID 用户组ID
     * @return 返回拼凑后的url
     */
    public static String getBHZEquipment(String userGroupID) {
        String url = COMM_DEVICE.replace("%1", userGroupID);
        KLog.e(TAG, "拌合站设备列表:" + url);
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        return url;
    }

    /**
     * 按试验种类获取条目
     */
    public static final String SYS_Items = BaseURL + "sysController.do?sysHome&userGroupId=%1&startTime=%2&endTime=%3";

    /**
     * 试验室主页
     *
     * @param userGroupID 组织ID
     * @param startTime   查询的起始时间
     * @param endTime     查询的结束时间
     * @return 返回拼凑后的url
     */
    public static String getSYSLingdaoData(String userGroupID, String startTime, String endTime) {
        startTime = DateUtils.ChangeTimeToLong(startTime);
        endTime = DateUtils.ChangeTimeToLong(endTime);
        //如果开始时间大于结束时间，返回null
        if (Integer.valueOf(startTime) <= Integer.valueOf(endTime)) {
            String url = SYS_Items.replace("%1", userGroupID).replace("%2", startTime).replace("%3", endTime);
            KLog.e(TAG, "试验室主界面URL :" + url);
            if (TextUtils.isEmpty(url)) {
                return null;
            }
            return url;
        }
        return null;
    }


    /**
     * 拌合站菜单界面
     */
    public static final String Menu_SYS = BaseURL + "sysController.do?sysMainLogo&userGroupId=%1";

    /**
     * 拌合站菜单界面
     */
    public static final String Menu_BHZ = BaseURL + "app.do?hntMainLogo&userGroupId=%1";

    /**
     * 拌合站统计信息
     */
    public static final String BHZ_Lingdao = BaseURL + "app.do?AppHntMain&departId=%1&startTime=%2&endTime=%3";

    /**
     * 混凝土主页
     *
     * @param userGroupID 组织ID
     * @param startTime   查询的起始时间
     * @param endTime     查询的结束时间
     * @return 返回拼凑后的url
     */
    public static String getBHZLingdaoData(String userGroupID, String startTime, String endTime) {
        startTime = DateUtils.ChangeTimeToLong(startTime);
        endTime = DateUtils.ChangeTimeToLong(endTime);
        //如果开始时间大于结束时间，返回null
        if (Integer.valueOf(startTime) <= Integer.valueOf(endTime)) {
            String url = BHZ_Lingdao.replace("%1", userGroupID).replace("%2", startTime).replace("%3", endTime);
            KLog.e(TAG, "混凝土拌合站主界面URL :" + url);
            if (TextUtils.isEmpty(url)) {
                return null;
            }
            return url;
        }
        return null;
    }

    /**
     * 混泥土强度列表地址
     */
    public static final String HNT_URL = BaseURL + "sysController.do?hntkangya&userGroupId=%1&isQualified=%2&startTime=%3&endTime=%4&pageNo=%5&shebeibianhao=%6&isReal=%7&maxPageItems=30&testId=%8";


    /**
     * 得到压力机列表数据
     *
     * @param userGroupID    组织结构ID
     * @param isQualified    是否合格
     * @param startTime      开始时间
     * @param endTime        结束时间
     * @param current_PageNo 当前页码
     * @param deviceNo       设备编号
     * @param isReal
     * @param testType       试验类型
     * @return url
     */
    public static String getYalijiTestList(String userGroupID, String isQualified, String startTime, String endTime, String current_PageNo, String deviceNo, String isReal, String testType) {
        startTime = DateUtils.ChangeTimeToLong(startTime);
        endTime = DateUtils.ChangeTimeToLong(endTime);
        //如果开始时间大于结束时间，返回null
        if (Integer.valueOf(startTime) <= Integer.valueOf(endTime)) {
            String url = HNT_URL.replace("%1", userGroupID).replace("%2", isQualified).replace("%3", startTime).replace("%4", endTime).replace("%5", current_PageNo).replace("%6", deviceNo).replace("%7", isReal).replace("%8", testType);
            KLog.e(TAG, "试验室压力试验列表 :" + url);
            if (TextUtils.isEmpty(url)) {
                return null;
            }
            return url;
        }
        return null;
    }

    /**
     * 混泥土强度详情地址
     */
    public static final String HNTXQ_URL = BaseURL + "sysController.do?hntkangyaDetail&SYJID=%1";

    /**
     * 压力试验详情
     *
     * @param detailID 详情ID
     * @return 返回拼凑后的url
     */
    public static String getYalijiDetailData(String detailID) {
        String url = HNTXQ_URL.replace("%1", detailID);
        KLog.e(TAG, "压力试验详情 :" + url);
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        return url;
    }

    /**
     * 钢筋拉力列表地址
     */
    public static final String GJLL_URL = BaseURL + "sysController.do?gangjin&userGroupId=%1&isQualified=%2&startTime=%3&endTime=%4&pageNo=%5&shebeibianhao=%6&isReal=%7&maxPageItems=15";

    /**
     * 万能机试验详情
     */
    public static final String GJLLXQ_URL = BaseURL + "sysController.do?gangjinDetail&SYJID=%1";

    /**
     * 万能机试验详情
     *
     * @param detailID 详情ID
     * @return 返回拼凑后的url
     */
    public static String getWannengjiDetailData(String detailID) {
        String url = GJLLXQ_URL.replace("%1", detailID);
        KLog.e(TAG, "万能机试验详情 :" + url);
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        return url;
    }

    /**
     * 钢筋焊接接头列表地址
     */
    public static final String GJHJJT_URL = BaseURL + "sysController.do?gangjinhanjiejietou&userGroupId=%1&isQualified=%2&startTime=%3&endTime=%4&pageNo=%5&shebeibianhao=%6&isReal=%7&maxPageItems=15";

    /**
     * 钢筋焊接接头详情地址
     */
    public static final String GJHJJTXQ_URL = BaseURL + "sysController.do?gangjinhanjiejietouDetail&SYJID=%1";

    /**
     * 钢筋机械连接接头列表地址
     */
    public static final String GJJXLJJT_URL = BaseURL + "sysController.do?gangjinlianjiejietou&userGroupId=%1&isQualified=%2&startTime=%3&endTime=%4&pageNo=%5&shebeibianhao=%6&isReal=%7&maxPageItems=15";

    /**
     * 拌合站生产数据查询
     */
    public static final String BHZ_SCDATA_URL = BaseURL + "app.do?AppHntXiangxiList&departId=%1&startTime=%2&endTime=%3&pageNo=%4&shebeibianhao=%5&maxPageItems=30";

    /**
     * 拌合站生产数据查询
     *
     * @param userGroupID    组织结构ID
     * @param startTime      开始时间
     * @param endTime        结束时间
     * @param current_PageNo 当前页码
     * @param deviceNo       设备编号
     * @return url
     */
    public static String getProduceData(String userGroupID, String startTime, String endTime, String current_PageNo, String deviceNo) {
        startTime = DateUtils.ChangeTimeToLong(startTime);
        endTime = DateUtils.ChangeTimeToLong(endTime);
        //如果开始时间大于结束时间，返回null
        if (Integer.valueOf(startTime) <= Integer.valueOf(endTime)) {
            String url = BHZ_SCDATA_URL.replace("%1", userGroupID).replace("%2", startTime).replace("%3", endTime).replace("%4", current_PageNo).replace("%5", deviceNo);
            KLog.e(TAG, "拌合站生产数据查询URL :" + url);
            if (TextUtils.isEmpty(url)) {
                return null;
            }
            return url;
        }
        return null;
    }


    /**
     * 拌合站生产数据详情查询
     */
    public static final String BHZ_SCDATA_DETAIL_URL = BaseURL + "app.do?AppHntXiangxiDetail&bianhao=%1";

    /**
     * 拌合站生产数据详情查询
     *
     * @param detailID 详情ID
     * @return 返回拼凑后的url
     */
    public static String getProduceDetailData(String detailID) {
        String url = BHZ_SCDATA_DETAIL_URL.replace("%1", detailID);
        KLog.e(TAG, "拌合站生产数据详情查询 :" + url);
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        return url;
    }


    /**
     * 拌合站待处置超标列表
     */
    public static final String BHZ_CHAOBIAO_LIST_URL = BaseURL + "app.do?AppHntChaobiaoList&departId=%1&startTime=%2&endTime=%3&dengji=%4&chuzhileixing=%5&pageNo=%6&shebeibianhao=%7&maxPageItems=30";

    /**
     * 拌合站待处置超标列表
     *
     * @param userGroupID    组织结构ID
     * @param startTime      开始时间
     * @param endTime        结束时间
     * @param current_PageNo 当前页码
     * @param deviceNo       设备编号
     * @return url
     */
    public static String getOverproofData(String userGroupID, String startTime, String endTime, String dengji, String chuzhileixing, String current_PageNo, String deviceNo) {
        startTime = DateUtils.ChangeTimeToLong(startTime);
        endTime = DateUtils.ChangeTimeToLong(endTime);
        //如果开始时间大于结束时间，返回null
        if (Integer.valueOf(startTime) <= Integer.valueOf(endTime)) {
            String url = BHZ_CHAOBIAO_LIST_URL.replace("%1", userGroupID).replace("%2", startTime).replace("%3", endTime).replace("%4", dengji).replace("%5", chuzhileixing).replace("%6", current_PageNo).replace("%7", deviceNo);
            KLog.e(TAG, "拌合站待处置超标列表:" + url);
            if (TextUtils.isEmpty(url)) {
                return null;
            }
            return url;
        }
        return null;
    }


    /**
     * 拌合站待处置超标详情
     */
    public static final String BHZ_CHAOBIAO_DETAIL_URL = BaseURL + "app.do?AppHntChaobiaoDetail&bianhao=%1";

    /**
     * 拌合站待处置超标详情
     *
     * @param detailID 详情ID
     * @return 返回拼凑后的url
     */
    public static String getOverproofDetailData(String detailID) {
        String url = BHZ_CHAOBIAO_DETAIL_URL.replace("%1", detailID);
        KLog.e(TAG, "拌合站待处置超标详情 :" + url);
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        return url;
    }

    /**
     * 混凝土超标处置
     */
    public static final String BHZ_CHAOBIAO_DO_URL = BaseURL + "app.do?AppHntChaobiaoChuzhi&jieguobianhao=%1&chaobiaoyuanyin=%2&chuzhifangshi=%3&chuzhijieguo=%4&chuzhiren=%5&chuzhishijian=%6";

    /**
     * 混凝土综合统计分析
     */
    public static final String BHZ_ZONGHT_TJ_URL = BaseURL + "app.do?hntCountAnalyze&userGroupId=%1&startTime=%2&endTime=%3&shebeibianhao=%4&cllx=%5";

    /**
     * 混凝土材料用量
     */
    public static final String BHZ_CAILIAO_URL = BaseURL + "app.do?AppHntMaterial&departId=%1&startTime=%2&endTime=%3&shebeibianhao=%4";

    /**
     * 混凝土材料用量
     *
     * @param userGroupID 组织ID
     * @param startTime   查询的起始时间
     * @param endTime     查询的结束时间
     * @return 返回拼凑后的url
     */
    public static String getBHZCailiaoyongliang(String userGroupID, String startTime, String endTime, String equipmentID) {
        startTime = DateUtils.ChangeTimeToLong(startTime);
        endTime = DateUtils.ChangeTimeToLong(endTime);
        //如果开始时间大于结束时间，返回null
        if (Integer.valueOf(startTime) <= Integer.valueOf(endTime)) {
            String url = BHZ_CAILIAO_URL.replace("%1", userGroupID).replace("%2", startTime).replace("%3", endTime).replace("%4", equipmentID);
            KLog.e(TAG, "混凝土材料用量URL :" + url);
            if (TextUtils.isEmpty(url)) {
                return null;
            }
            return url;
        }
        return null;
    }


    /**
     * 试验室超标处置
     */
    public static final String SYS_CHAOBIAO_DO_URL = BaseURL + "sysController.do?hntkangyaPost";

    /**
     * 拌合站状态
     */
    public static final String COMM_BHZ_STS = BaseURL + "app.do?AppHntBanhejiState&departId=%1&pageNo=%2&maxPageItems=30";

    /**
     * 拌合站超标审批
     */
//    public static final String BHZ_CHAOBIAO_SP = BaseURL + "app.do?AppHntChaobiaoShenpi&jieguobianhao=%1&jianliresult=%2&jianlishenpi=%3&confirmdate=%4&shenpiren=%5&shenpidate=%6";
    public static final String BHZ_CHAOBIAO_SP = BaseURL + "app.do?AppHntChaobiaoShenpi";
    /**
     * 试验室设备列表
     */
    public static final String SYS_SHEBEI_LIST = BaseURL + "sysController.do?getSysShebeiList&userGroupId=%1";

    /**
     * 登录验证
     *
     * @param userGroupID 用户组ID
     * @return 返回拼凑后的url
     */
    public static String getEquipment(String userGroupID) {
        String url = SYS_SHEBEI_LIST.replace("%1", userGroupID);
        KLog.e(TAG, "获取设备的URL:" + url);
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        return url;
    }

    /**
     * 试验室试验类型列表
     */
    public static final String SYS_SHEBEI_TEST_LIST = BaseURL + "sysController.do?getSyLx";

    public static String getTestType(String userGroupID) {
        String url = SYS_SHEBEI_TEST_LIST.replace("%1", userGroupID);
        KLog.e(TAG, "试验室试验类型列表URL:" + url);
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        return url;
    }

    /**
     * 试验室综合统计分析
     */
    public static final String SYS_TONGJI_FENXI = BaseURL + "sysController.do?sysCountAnalyze&userGroupId=%1&startTime=%2&endTime=%3";

    public static String getLaboratoryStatistic(String userGroupID, String startTime, String endTime) {
        startTime = DateUtils.ChangeTimeToLong(startTime);
        endTime = DateUtils.ChangeTimeToLong(endTime);
        //如果开始时间大于结束时间，返回null
        if (Integer.valueOf(startTime) <= Integer.valueOf(endTime)) {
            String url = SYS_TONGJI_FENXI.replace("%1", userGroupID).replace("%2", startTime).replace("%3", endTime);
            KLog.e(TAG, "试验室统计分析 :" + url);
            if (TextUtils.isEmpty(url)) {
                return null;
            }
            return url;
        }
        return null;
    }

    /**
     * 钢筋列表地址
     */
    public static final String GJ_URL = BaseURL + "sysController.do?gangjin&userGroupId=%1&isQualified=%2&startTime=%3&endTime=%4&pageNo=%5&shebeibianhao=%6&isReal=%7&maxPageItems=30&testId=%8";

    /**
     * 得到万能机列表数据
     *
     * @param userGroupID    组织结构ID
     * @param isQualified    是否合格
     * @param startTime      开始时间
     * @param endTime        结束时间
     * @param current_PageNo 页码
     * @param deviceNo       设备编号
     * @param isReal         是否处置
     * @param testType       试验类型
     * @return url
     */
    public static String getWannengjiTestList(String userGroupID, String isQualified, String startTime, String endTime, String current_PageNo, String deviceNo, String isReal, String testType) {
        startTime = DateUtils.ChangeTimeToLong(startTime);
        endTime = DateUtils.ChangeTimeToLong(endTime);
        //如果开始时间大于结束时间，返回null
        if (Integer.valueOf(startTime) <= Integer.valueOf(endTime)) {
            String url = GJ_URL.replace("%1", userGroupID).replace("%2", isQualified).replace("%3", startTime).replace("%4", endTime).replace("%5", current_PageNo).replace("%6", deviceNo).replace("%7", isReal).replace("%8", testType);
            KLog.e("万能机列表 :" + url);
            if (TextUtils.isEmpty(url)) {
                return null;
            }
            return url;
        }
        return null;
    }

    /**
     * 获取试验室设备和试验类型
     */
    public static final String EQUIPMENT_TESTTYPE_URL = BaseURL + "sysController.do?getSyTpAndMc&userGroupId=%1";

    /**
     * 获取试验室设备和试验类型
     *
     * @param userGroupID 用户组ID
     * @return 返回拼凑后的url
     */
    public static String getLibEquipmentTest(String userGroupID) {
        String url = EQUIPMENT_TESTTYPE_URL.replace("%1", userGroupID);
        KLog.e(TAG, "试验室设备和试验类型:" + url);
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        return url;
    }

    /**
     * 试验室施工用户主页数据
     */
    public static final String SG_SYS_MAIN = BaseURL + "sysController.do?hntSysMainLogo&userGroupId=%1";

    public static String getLibSGMain(String userGroupID) {
        String url = SG_SYS_MAIN.replace("%1", userGroupID);
        KLog.e(TAG, "试验室施工用户主页数据:" + url);
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        return url;
    }

    /**
     * 拌和站施工用户主页数据
     */
    public static final String SG_BHZ_MAIN = BaseURL + "app.do?hntBhzMainLogo&userGroupId=%1";

    public static String getBHZSGMain(String userGroupID) {
        String url = SG_BHZ_MAIN.replace("%1", userGroupID);
        KLog.e(TAG, "拌和站施工用户主页数据:" + url);
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        return url;
    }

    /**
     * 原材料进场数据查询
     */
    public static String YCLJINCHANG_QUERY = BaseURL +"AppWZYCLJinChangGBCountInterfaceController.do?AppWZYCLJinChangGBCount&departId=%1&cailiaomingcheng=%2&gprsbianhao=%3&tongjitype=%4&jinchangshijian1=%5&jinchangshijian2=%6&pageNo=%7&maxPageItems=10";


    public static String getYCLJINCHANGquery(String departId,String cailiaomingcheng,String gprsbianhao,String tongjitype,String jinchangshijian1,String jinchangshijian2,String pageNo) {
        jinchangshijian1 = DateUtils.ChangeTimeToLong(jinchangshijian1);
        jinchangshijian2 = DateUtils.ChangeTimeToLong(jinchangshijian2);

        //如果开始时间大于结束时间，返回null
        if (Integer.valueOf(jinchangshijian1) <= Integer.valueOf(jinchangshijian2)) {
            String url = YCLJINCHANG_QUERY.replace("%1", departId).replace("%2", cailiaomingcheng).replace("%3", gprsbianhao).replace("%4", tongjitype).replace("%5", jinchangshijian1).replace("%6", jinchangshijian2).replace("%7", pageNo);
            KLog.e(TAG, "原材料进场过账数据:" + url);
            if (TextUtils.isEmpty(url)) {
                return null;
            }
            Log.e("原材料进场过账数据", url);
            return url;
        }
        return null;
    }

    /**
     * 进厂数据详情查询
     */
    public static final String YCL_JINCHANG_DETAIL = BaseURL + "AppWZYCLJinChangGBCountInterfaceController.do?AppWZYCLJinChangGBDetail&jinchuliaodanno=%1&cailiaono=%2&gongyingshangdanweibianma=%3&pici=%4&shebeibianhao=%5&jcmin=%6&jcmax=%7&ccmin=%8&ccmax=%9";

    /**
     * 进厂生产数据详情查询
     *
     * @param
     * @return 返回拼凑后的url
     */
    public static String getYclJinchangDetailData(String jinchuliaodanno,String cailiaono,String gongyingshangdanweibianma,String pici,String shebeibianhao,String jcmin,String jcmax,String ccmin,String ccmax) {
        String url = YCL_JINCHANG_DETAIL.replace("%1", jinchuliaodanno).replace("%2", cailiaono).replace("%3", gongyingshangdanweibianma).replace("%4",pici).replace("%5",shebeibianhao).replace("%6", jcmin).replace("%7", jcmax).replace("%8", ccmin).replace("%9", ccmax);
        KLog.e(TAG, "原材料进场数据详情查询 :" + url);
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        Log.e("原材料进场数据详情查询",url);
        return url;
    }

    /**
     * 磅房列表
     */
    public static final String WAAG_LIST = BaseURL + "AppGB.do?AppDiBangList&departId=%1";

    public static String getWaagList(String departId) {
        String url = WAAG_LIST.replace("%1", departId);
        KLog.e(TAG, "磅房列表:" + url);
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        return url;
    }

    /**
     * 材料列表
     */
    public static final String MATERIAL_LIST = BaseURL + "appWZproject.do?AppCaiLiaoNameDict";


    /**
     * 原材料出场数据查询
     */
    public static String YCLCHUCHANG_QUERY = BaseURL +"AppWZYCLChuChangGBCountInterfaceController.do?AppWZYCLChuChangGBCount&departId=%1&cailiaomingcheng=%2&gprsbianhao=%3&tongjitype=%4&chuchangshijian1=%5&chuchangshijian2=%6&states=%7&pageNo=%8&maxPageItems=10";


    public static String getYCLCHUCHANGquery(String departId,String cailiaomingcheng,String gprsbianhao,String tongjitype,String jinchangshijian1,String jinchangshijian2,String states,String pageNo) {
        jinchangshijian1 = DateUtils.ChangeTimeToLong(jinchangshijian1);
        jinchangshijian2 = DateUtils.ChangeTimeToLong(jinchangshijian2);

        //如果开始时间大于结束时间，返回null
        if (Integer.valueOf(jinchangshijian1) <= Integer.valueOf(jinchangshijian2)) {
            String url = YCLCHUCHANG_QUERY.replace("%1", departId).replace("%2", cailiaomingcheng).replace("%3", gprsbianhao).replace("%4", tongjitype).replace("%5", jinchangshijian1).replace("%6", jinchangshijian2).replace("%7", states).replace("%8",pageNo);
            KLog.e(TAG, "原材料出厂过账数据:" + url);
            if (TextUtils.isEmpty(url)) {
                return null;
            }
            Log.e("原材料出厂过账数据", url);
            return url;
        }
        return null;
    }

    /**
     * 出厂数据详情查询
     */
    public static final String YCL_CHUCHANG_DETAIL = BaseURL + "AppWZYCLChuChangGBCountInterfaceController.do?AppWZYCLChuChangGBDetail&id=%1&guobangleibie=%2&cailiaono=%3&gongyingshangdanweibianma=%4&pici=%5&shebeibianhao=%6&jcmin=%7&jcmax=%8&ccmin=%9&ccmax=@10";

    /**
     * 出厂生产数据详情查询
     *
     * @param
     * @return 返回拼凑后的url
     */
    public static String getYclChuchangDetailData(String id,String guobangleibie,String cailiaono,String gongyingshangdanweibianma,String pici,String shebeibianhao,String jcmin,String jcmax,String ccmin,String ccmax) {
        String url = YCL_CHUCHANG_DETAIL.replace("%1",id).replace("%2",guobangleibie).replace("%3",cailiaono).replace("%4",gongyingshangdanweibianma).replace("%5",pici).replace("%6",shebeibianhao).replace("%7",jcmin).replace("%8", jcmax).replace("%9",ccmin).replace("@10",ccmax);
        KLog.e(TAG, "原材料出场数据详情查询 :" + url);
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        Log.e("原材料出场数据详情查询",url);
        return url;
    }

    /**
     * 工程进度查询查询
     */
    public static final String WZ_PROJECTPROGRESS = BaseURL+"AppWZprojectProgressQueryController.do?query&parentno=%1&pageNo=%2&maxPageItems=10";

    /**
     * 工程进度查询查询
     *
     * @param
     * @return 返回拼凑后的url
     */
    public static String getWZprojectprogress(String parentno,String pageNo) {
        String url = WZ_PROJECTPROGRESS.replace("%1",parentno).replace("%2",pageNo);
        KLog.e(TAG, "工程进度查询查询 :" + url);
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        Log.e("原材料出场数据详情查询",url);
        return url;
    }

    /**
     * 分部分项列表
     */
    public static final String FB_PROJECT = BaseURL+"appWZproject.do?Appfenbufenxiang";
}

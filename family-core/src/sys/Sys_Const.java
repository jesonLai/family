package sys;

/**
 * 系统常量
 * 
 * @author lxr
 *
 */
public interface Sys_Const {
    // >>>>>>>>>>>>>>>>>>>>>>>功能管理<<<<<<<<<<<<<<<<<<<<<<<<<<
    static final String META_LOG_REQ_SAVEPUBAUTHORITIESFUNCTION = "sys_admin_controller_addNewAuthorityFunction";

    static final String META_LOG_SAVEPUBAUTHORITIESFUNCTION = "sys_admin_service_addNewAuthorityFunction";
    // >>>>>>>>>>>>>>>>>>>>>>>菜单管理<<<<<<<<<<<<<<<<<<<<<<<<<<

    static final String META_LOG_USERALLPAGE = "sys_admin_controller_usersMainPage";
    static final String META_LOG_USERLIST = "sys_admin_controller_usersList";
    static final String META_LOG_USERADDPAGE = "sys_admin_controller_usersAddPage";
    static final String META_LOG_ROLEMAINPAGE = "sys_admin_controller_RoleMainPage";
    static final String META_LOG_ROLESELECT = "sys_admin_controller_roleselect";
    static final String META_LOG_ROLELIST = "sys_admin_controller_roleList";
    static final String META_LOG_SAVEMENU = "sys_admin_controller_saveMenu";
    static final String META_LOG_REMOVEMENU = "sys_admin_controller_removeMenu";

    static final String META_LOG_SERVICE_SAVENEWMENU = "sys_admin_service_addNewMenu";
    static final String META_LOG_SERVICE_MENUPAGINATION = "sys_admin_service_MenuPagination";
    static final String META_LOG_SERVICE_MENUSLIST = "sys_admin_service_menusList";
    static final String META_LOG_SERVICE_ROLESPAGINATION = "sys_admin_service_rolesPagination";
    static final String META_LOG_SERVICE_GRANTROLE = "sys_admin_service_grantRole";
    static final String META_LOG_SERVICE_REMOVEMENU = "sys_admin_service_removeMenu";
    static final String META_LOG_SERVICE_ONEMENU = "sys_admin_service_oneMenu";
    static final String META_LOG_SERVICE_UPMOVE = "sys_admin_service_UpMenu";
    static final String META_LOG_SERVICE_DOWNMOVE = "sys_admin_service_DownMenu";

    // >>>>>>>>>>>>>>>>>>>>>>>登录管理<<<<<<<<<<<<<<<<<<<<<<<<<<<
    static final String META_LOG_CONTROLLER_USERLOGININFOMAINPAGE = "sys_admin_controller_userLoginInfoMainPage";
    static final String META_LOG_CONTROLLER_USERLOGININFOMAINLIST = "sys_admin_controller_userLoginInfoMainList";
    static final String META_LOG_CONTROLLER_USERLOGININFOADDNEWPAGE = "sys_admin_controller_userLoginInfoAddNewPage";
    static final String META_LOG_CONTROLLER_USERLOGININFOUPDATEPASSWORD = "sys_admin_controller_userLoginInfoUpdatePassWord";
    static final String META_LOG_CONTROLLER_USERLOGININFORESETPASSWORD = "sys_admin_controller_userLoginInfoResetPassWord";
    static final String META_LOG_CONTROLLER_USERLOGININFREMOVE = "sys_admin_controller_userLoginInfoRemove";

    // >>>>>>>>>>>>>>>>>>>>>>>用户管理<<<<<<<<<<<<<<<<<<<<<<<<<<<
    static final String META_LOG_CONTROLLER_USERINFOMAINPAGE = "sys_admin_controller_userInfoMainPage";
    static final String META_LOG_CONTROLLER_USERINFOMAINLIST = "sys_admin_controller_usersInfoMainList";
    static final String META_LOG_CONTROLLER_USERINFOIMPORTEXCELPAGE = "sys_admin_controller_usersInfoImportExcelPage";
    static final String META_LOG_CONTROLLER_USERINFOIMPORTEXCELDATAPAGE = "sys_admin_controller_usersInfoDataImportExcelPage";
    static final String META_LOG_CONTROLLER_USERINFOFATHERSELET = "sys_admin_controller_usersInfoFatherSelect";
    static final String META_LOG_CONTROLLER_USERINFOFATHERSELETINIT = "sys_admin_controller_usersInfoFatherSelectInit";
    static final String META_LOG_CONTROLLER_USERINFOADDNEWORUPDATEPAGE = "sys_admin_controller_usersInfoAddNewOrUpdate";
    static final String META_LOG_CONTROLLER_USERINFODELETE = "sys_admin_controller_usersInfoDelete";
    static final String META_LOG_CONTROLLER_USERINFODADDNEWBATCH = "sys_admin_controller_usersInfoAddNewBatch";
    static final String META_LOG_CONTROLLER_DOENLOADEXCELUSERINFOTEMPLATE = "sys_admin_controller_downLoadExcelUserInfoTemplate";
    static final String META_LOG_CONTROLLER_GETUPDATEUSERINFODATE = "sys_admin_controller_getUpdateUserInfoDate";
    static final String META_LOG_CONTROLLER_UPLOADUSERINFOHEADPORTRAIT = "sys_admin_controller_uploadHeadPortrait";

    // >>>>>>>>>>>>>>>>>>>>>>>新闻管理<<<<<<<<<<<<<<<<<<<<<<<<<<<
    // >>>>>>>>>>>>>>>>>>>>>>>家族族谱<<<<<<<<<<<<<<<<<<<<<<<<<<<
    // >>>>>>>>>>>>>>>>>>>>>>>成员认证<<<<<<<<<<<<<<<<<<<<<<<<<<<
    // >>>>>>>>>>>>>>>>>>>>>>>留言版<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    // >>>>>>>>>>>>>>>>>>>>>>>功德榜<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    // >>>>>>>>>>>>>>>>>>>>>>>公告管理<<<<<<<<<<<<<<<<<<<<<<<<<<<
    // >>>>>>>>>>>>>>>>>>>>>>>家族文化<<<<<<<<<<<<<<<<<<<<<<<<<<<
    // >>>>>>>>>>>>>>>>>>>>>>>家族名人<<<<<<<<<<<<<<<<<<<<<<<<<<<
    // >>>>>>>>>>>>>>>>>>>>>>>财务管理<<<<<<<<<<<<<<<<<<<<<<<<<<<
    // >>>>>>>>>>>>>>>>>>>>>>>视频管理<<<<<<<<<<<<<<<<<<<<<<<<<<<
    // >>>>>>>>>>>>>>>>>>>>>>>图片管理<<<<<<<<<<<<<<<<<<<<<<<<<<<
    static final String FAMILY_HEAD_IMAGE_FILE_FOLDER_PATH = "monkey{0}image{0}headImage";
    static final String FAMILY_NEWS_FILE_FOLDER_PATH = "monkey{0}image{0}fmailyNew";
    static final String FAMILY_IMAGES_FILE_FOLDER_PATH = "monkey{0}image{0}familyImages";
    static final String FAMILY_VIDEO_FILE_FOLDER_PATH = "monkey{0}image{0}familyVideo";
    static final String FAMILY_CELEBRITY_FILE_FOLDER_PATH = "monkey{0}image{0}celebrity";
    static final String FAMILY_ANCESTRALTEMPLE_FILE_FOLDER_PATH = "monkey{0}image{0}ancestralTemple";

}

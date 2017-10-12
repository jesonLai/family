package sys;

public interface SpringAnnotationDef {
    // -------------------------------------------------------------------------
	static String SCOPE_SINGLETON = "singleton";
	static String SCOPE_PROTOTYPE = "prototype";
	static String SCOPE_SESSION = "session";
	static String SCOPE_REQUEST = "request";
    //--------------------------------------------------------------------------
	static String REQ_POPEDOM = "popedomControl";
	static String REQ_JDBC = "dbcontext";
	static String USER_CONTEXT = "userContext";
	static String STORE_MANAGER = "storeManager";
	//====================系统设置=========================
	static String SER_FUNCTION = "FunctionsService";
	static String SER_ROLE = "RolesService";
	static String SER_SYSCODE = "SysCodeService";
	static String SER_USERS = "UsersService";
	static String SER_USERSLOGIN = "UsersLoginService";
	
	static String SER_FAMILYNEWS = "FamilyNewsService";
	static String SER_FAMILYIMAGE = "FamilyImageService";
	static String SER_FAMILYVIDEO = "FamilyVideoService";
	static String SER_FAMILYCELEBRITY = "FamilyCelebrityService";
	static String SER_FAMILYCULTURE = "FamilyCultureService";
	static String SER_FAMILYFINANCIAL = "FamilyFinancialService";
	static String SER_FAMILYTREE = "FamilyTreeService";
	static String SER_FAMILYMESSAGE = "FamilyMessage";
	static String SER_REGISTER = "registerService";
	static String SER_ANNOUNCEMENT = "AnnouncementService";
	static String SER_ANCESTRALTEMPLE = "AncestralTempleService";
	static String SER_AUTHORITY = "AuthorityService";
	static String SER_AUTHORITY_GROUP = "AuthorityGroupService";
	static String SER_CONTRIBUTION="ContributionService";
	static String SER_ADVERTISEMENT="AdvertisementService";
	static String SER_ROLES="RolesService";
	static String SER_MENU="MenuService";
	static String SER_RECORDFAMILYLOG="RecordFamilyLog";
	
	
	//======================前台注册========================
	static String SER_REGISTER_FRONT="registerServiceFront";
	
	static String SER_RELATIONSHIP="RelationshipServiceFront";
	//====================前台=========================
	static String Web_SER_USERS = "WebUsersService";
	static String Web_SER_USERSINFO = "WebUsersInfoService";
	static String Web_SER_ANCESTRALTEMPLE = "WebAncestralTempleService";
	static String Web_SER_ANNOUNCEMENT = "WebAnnouncementService";
	static String Web_SER_FAMILYIMAGE = "WebFamilyImageService";
	static String Web_SER_FAMILYMESSAGE = "WebFamilyMessage";
	static String Web_SER_FAMILYNEWS = "WebFamilyNewsService";
	static String Web_SER_FAMILYVIDEO = "WebFamilyVideoService";
	static String Web_SER_FAMILYINSTATIONSEARCH = "WebInstationSearchService";
	//====================公共=========================
	static String Web_SER_INSTATIONSEARCH = "InstationSearchService";
}

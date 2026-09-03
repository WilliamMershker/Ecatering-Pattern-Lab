package vn.edu.patterrnsdemo.structural.Proxy;

public class AccessControlProxy implements ReportService
{
    private final RealReportService realService;
    private final String userRole;

    public AccessControlProxy(String userRole)
    {
        this.realService = new RealReportService();
        this.userRole = userRole;
    }

    @Override
    public String viewRevenueReport(int storeId)
    {
        if (!hasPermission())
        {
            throw new SecurityException(
                    "Truy cập bị từ chối: vai trò "
                            + userRole
                            + " không có quyền xem báo cáo doanh thu."
            );
        }

        StringBuilder result = new StringBuilder();

        result.append("[Proxy] Vai trò ")
                .append(userRole)
                .append(" có quyền truy cập.\n");

        result.append("[Proxy] Chuyển tiếp yêu cầu đến RealReportService...\n");

        result.append(realService.viewRevenueReport(storeId));

        return result.toString();
    }

    private boolean hasPermission()
    {
        return "ADMIN".equalsIgnoreCase(userRole)
                || "STORE_OWNER".equalsIgnoreCase(userRole);
    }
}
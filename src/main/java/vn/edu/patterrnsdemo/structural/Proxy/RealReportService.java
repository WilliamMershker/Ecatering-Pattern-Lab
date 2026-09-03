package vn.edu.patterrnsdemo.structural.Proxy;

public class RealReportService implements ReportService
{
    @Override
    public String viewRevenueReport(int storeId)
    {
        return "   -> [RealReportService] Doanh thu cửa hàng "
                + storeId
                + ": 45.000.000đ\n";
    }
}
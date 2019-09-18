package com.ams.utils;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.http.client.utils.DateUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.ams.department.entity.Department;
import com.ams.department.entity.Job;
import com.ams.utils.entity.ExcelBean;


public class ExcelUtil {
	private final static String excel2003L = ".xls";// 2003-版本的excel
	private final static String excel2007U = ".xlsx";// 2007+版本的excel
	private static XSSFCellStyle fontStyle; // 表头CellStyle
	private static XSSFCellStyle fontStyle2; // 内容CellStyle

	/**
	 * Excel导入 遍历Excel,将所有的信息存入List<List<Object>> 一个List<Object>表示一行
	 */
	public static List<List<Object>> getBankListByExcel(InputStream in, String fileName) throws Exception {
		List<List<Object>> list = null;// 存储表中数据
		// 创建Excel工作簿
		Workbook work = getWorkbook(in, fileName);// 根据文件名后缀判断EXCEL版本，创建对应版本的工作簿
		if (work == null)
			throw new Exception("创建Excel工作簿为空");
		Sheet sheet = null;// 表
		Row row = null;// 行
		Cell cell = null;// 列
		list = new ArrayList<List<Object>>();// 初始化
		// 遍历Excel中所有的sheet
		for (int i = 0; i < work.getNumberOfSheets(); i++) {
			sheet = work.getSheetAt(i);
			if (sheet == null) {
				continue;
			}
			// 遍历当前sheet中的所有行
			// 包含头部，所以要小于等于最后一列数，这里也可以在初始值加上头部行数，以便跳过头部
			for (int j = sheet.getFirstRowNum(); j <= sheet.getLastRowNum(); j++) {
				// 读取一行
				row = sheet.getRow(j);
				// 去掉空行和表头（第一行）
				if (row == null || row.getFirstCellNum() == j)
					continue;
				// 遍历所有的列
				List<Object> li = new ArrayList<Object>();
				for (int y = row.getFirstCellNum(); y < row.getLastCellNum(); y++) {
					cell = row.getCell(y);
					System.out.println(getCellValue(cell));
					li.add(getCellValue(cell));// 对表格数据进行格式化话加入li
				}
				list.add(li);
			}
		}
		return list;
	}

	/**
	 * 根据文件后缀，自适应上传文件的版本
	 */
	public static Workbook getWorkbook(InputStream inStr, String fileName) throws Exception {
		Workbook wb = null;
		String fileType = fileName.substring(fileName.lastIndexOf("."));
		if (excel2003L.equals(fileType)) {
			wb = new HSSFWorkbook(inStr); // 2003-
		} else if (excel2007U.equals(fileType)) {
			wb = new XSSFWorkbook(inStr); // 2007+
		} else {
			throw new Exception("解析的文件格式有误！");
		}
		return wb;

	}

	/**
	 * 对表格中数值进行格式化
	 */
	public static Object getCellValue(Cell cell) {
		Object value = null;
		DecimalFormat df = new DecimalFormat("0");// 格式化字符类型的数字
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// 日期格式化
		DecimalFormat df2 = new DecimalFormat("0.00");// 格式化数字
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			value = cell.getRichStringCellValue().getString();
			break;
		case Cell.CELL_TYPE_NUMERIC:
			if ("General".equals(cell.getCellStyle().getDataFormatString())) {
				value = df.format(cell.getNumericCellValue());
			} else if ("m/d/yy".equals(cell.getCellStyle().getDataFormatString())) {
				value = sdf.format(cell.getDateCellValue());
			} else {
				value = df2.format(cell.getNumericCellValue());
			}
			break;
		case Cell.CELL_TYPE_BOOLEAN:
			value = cell.getBooleanCellValue();
			break;
		case Cell.CELL_TYPE_BLANK:
			value = "";
			break;
		default:
			break;
		}
		return value;
	}

	/* 导入Excel表结束 */
	/**
	 * 导出Excel 多列头创建Excel
	 * 
	 * @param clazz     数据源Model类型
	 * @param objs      Excel标题列以及对应Model字段名
	 * @param map       标题列行数以及cell字体样式
	 * @param sheetName 工作簿名称
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws ClassNotFoundException
	 * @throws IntrospectionException
	 * @throws ParseException
	 */
	public static XSSFWorkbook createExcelFile(Class clazz, List objs, Map<Integer, List<ExcelBean>> map,
			String sheetName) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException,
			ClassNotFoundException, IntrospectionException, ParseException {
		// 创建新的Excel工作簿
		XSSFWorkbook workbook = new XSSFWorkbook();
		// 在Excel工作簿中建一工作表，其名为缺省值，也可指定Sheet名称
		XSSFSheet sheet = workbook.createSheet(sheetName);
		// 以下为Excel的字体样式以及Excel的标题与内容的创建，下面具体分析
		createFont(workbook);// 字体样式
		createTableHeader(sheet, map);// 创建标题
		createTableRows(sheet, map, objs, clazz);// 创建内容
		return workbook;
	}

	private static void createFont(XSSFWorkbook workbook) {
		// 表头
		fontStyle = workbook.createCellStyle();
		XSSFFont font1 = workbook.createFont();
		font1.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		font1.setFontName("黑体");
		font1.setFontHeightInPoints((short) 14);// 设置字体大小
		fontStyle.setFont(font1);
		fontStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);// 下边框
		fontStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);// 左边框
		fontStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);// 上边框
		fontStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);// 右边框
		fontStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);// 居中
		// 内容
		fontStyle2 = workbook.createCellStyle();
		XSSFFont font2 = workbook.createFont();
		font2.setFontName("宋体");
		font2.setFontHeightInPoints((short) 10);// 设置字体大小
		fontStyle2.setFont(font2);
		fontStyle2.setBorderBottom(XSSFCellStyle.BORDER_THIN);// 下边框
		fontStyle2.setBorderLeft(XSSFCellStyle.BORDER_THIN);// 左边框
		fontStyle2.setBorderTop(XSSFCellStyle.BORDER_THIN);// 上边框
		fontStyle2.setBorderRight(XSSFCellStyle.BORDER_THIN);// 右边框
		fontStyle2.setAlignment(XSSFCellStyle.ALIGN_CENTER);// 居中
	}

	/**
	 * 根据ExcelMapping生成列头（多行列头）
	 * 
	 * @param sheet 工作簿
	 * @param map   每行每个单元格对应的列头信息
	 */
	private static void createTableHeader(XSSFSheet sheet, Map<Integer, List<ExcelBean>> map) {
		int startIndex = 0;// cell起始位置
		int endIndex = 0;// cell终止位置
		for (Map.Entry<Integer, List<ExcelBean>> entry : map.entrySet()) {// 遍历map
			XSSFRow row = sheet.createRow(entry.getKey());// 根据map中的key值创建第几行
			List<ExcelBean> excels = entry.getValue();// 获取对应key中的List<ExcelBean>
			for (int x = 0; x < excels.size(); x++) {
				// 合并单元格
				if (excels.get(x).getCols() > 1) {// 需要合并单元格
					if (x == 0) {// x==0即元素为第一列
						endIndex += excels.get(x).getCols() - 1;
						CellRangeAddress range = new CellRangeAddress(0, 0, startIndex, endIndex);// 合并单元格
						sheet.addMergedRegion(range);
						startIndex += excels.get(x).getCols();
					} else {
						endIndex += excels.get(x).getCols();
						CellRangeAddress range = new CellRangeAddress(0, 0, startIndex, endIndex);
						sheet.addMergedRegion(range);
						startIndex += excels.get(x).getCols();
					}
					XSSFCell cell = row.createCell(startIndex - excels.get(x).getCols());
					cell.setCellValue(excels.get(x).getHeadTextName());// 设置内容
					if (excels.get(x).getCellStyle() != null)
						cell.setCellStyle(excels.get(x).getCellStyle());// 设置格式
					cell.setCellStyle(fontStyle);
				} else {// 不需要合并单元格
					XSSFCell cell = row.createCell(x);
					cell.setCellValue(excels.get(x).getHeadTextName());// 设置内容
					if (excels.get(x).getCellStyle() != null)
						cell.setCellStyle(excels.get(x).getCellStyle());// 设置格式
					cell.setCellStyle(fontStyle);
				}
			}
		}

	}

	/**
	 * 根据ExcelMaping创建内容
	 * 
	 * @param sheet
	 * @param map
	 * @param objs
	 * @param clazz
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws IntrospectionException
	 * @throws ClassNotFoundException
	 * @throws ParseException
	 */
	private static void createTableRows(XSSFSheet sheet, Map<Integer, List<ExcelBean>> map, List objs, Class clazz)
			throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, IntrospectionException,
			ClassNotFoundException, ParseException {
		int rowIndex = map.size();
		int maxKey = 0;// map中最大的KEY即标题所占行数
		List<ExcelBean> ems = new ArrayList<>();
		for (Map.Entry<Integer, List<ExcelBean>> entry : map.entrySet()) {// 判断取最大key
			if (entry.getKey() > maxKey)
				maxKey = entry.getKey();
		}
		ems = map.get(maxKey);// 最大key值所对应的List<ExcelBean> ems
		List<Integer> widths = new ArrayList<Integer>(ems.size());// 根据ems的容量创建整型数组
		for (Object obj : objs) {// 遍历List
			XSSFRow row = sheet.createRow(rowIndex);
			for (int i = 0; i < ems.size(); i++) {
				ExcelBean em = (ExcelBean) ems.get(i);
				// 获得get方法
				PropertyDescriptor pd = new PropertyDescriptor(em.getPropertyName(), clazz);// 字段名要与实体类中属性一致（因为此处使用字段名去匹配通过内省类中得到的属性getter/setter方法）
				Method getMethod = pd.getReadMethod();
				Object rtn = getMethod.invoke(obj);// 执行实体类的getter方法去获取值
				String value = "";
				// 如果是日期类型进行转换
				if (rtn != null) {
					if (rtn instanceof Date) {
						value = DateUtils.formatDate((Date) rtn, "yyyy-MM-dd");
					} else if (rtn instanceof BigDecimal) {
						NumberFormat nf = new DecimalFormat("#，##0.00");
						value = nf.format((BigDecimal) rtn).toString();
					} else if ((rtn instanceof Integer) && (Integer.valueOf(rtn.toString()) < 0)) {
						value = "--";
					}else if(rtn instanceof Department) {//如果是引用类型(部门类)进行转换
						value=((Department) rtn).getDepartmentName();
					}else if(rtn instanceof Job) {//如果是引用类型(职位类)进行转换
						value=((Job) rtn).getJobName();
					}else {
						value = rtn.toString();
					}
				}
				XSSFCell cell = row.createCell(i);
				cell.setCellValue(value);
				cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				cell.setCellStyle(fontStyle2);
				// 获得最大列宽
				int width = value.getBytes().length * 300;
				// 还未设置，设置当前
				if (widths.size() <= i) {
					widths.add(width);
					continue;
				}
				// 比原来大更新数据
				if (width > widths.get(i)) {
					widths.set(i, width);
				}
			}
			rowIndex++;
		}
		// 设置列宽
		for (int index = 0; index < widths.size(); index++) {
			Integer width = widths.get(index);
			width = width < 2500 ? 2500 : width + 300;
			width = width > 10000 ? 10000 + 300 : width + 300;
			sheet.setColumnWidth(index, width);
		}

	}

}

package com.bookshop.common.base;

import com.bookshop.goods.vo.ImageFileVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

public class BaseController {
    private static final String CURR_IMAGE_REPO_PATH = "/Users/lee/Desktop/shopping/file_repo";  // 이미지 repository

    protected List<ImageFileVO> upload(MultipartHttpServletRequest multipartHttpServletRequest) throws Exception{
        List<ImageFileVO> fileList = new ArrayList<ImageFileVO>();   // 이미지 파일 리스트 배열을 fileList에 저장
        Iterator<String> fileNames = multipartHttpServletRequest.getFileNames();   // 파일이름을 받아 fileNames에 저장
        while (fileNames.hasNext()){        // 반복문, fileNames의 마지막까지
            ImageFileVO imageFileVO = new ImageFileVO();        // imageFileVO 파라미터 생성
            String fileName = fileNames.next();                 // fileName 파라미터 생성
            imageFileVO.setFileType(fileName);                  // 파일 타입 지정
            MultipartFile mFile = multipartHttpServletRequest.getFile(fileName); // servlet으로 호출
            String originalFileName = mFile.getOriginalFilename(); // originalFileName 파라미터 생성
            imageFileVO.setFileType(originalFileName);          // imageFileVO의 fileType 설정
            fileList.add(imageFileVO);                          // 파일리스트에 추가

            File file = new File(CURR_IMAGE_REPO_PATH + "/" + fileName); // repository에 새 파일 생성
            if (mFile.getSize()!=0){            // file이 있다면
                if (! file.exists()){           // 경로에 파일지 존재하지 않다면
                    if (file.getParentFile().mkdirs()){     // 폴더 생성
                        file.createNewFile();               // 파일 생성
                    }
                }
                mFile.transferTo(new File(CURR_IMAGE_REPO_PATH + "/" + "temp" + "/" + originalFileName));  // 임시로 저장된 파일을 실제로 전송
            }
        }
        return fileList;
    }

    private void deleteFile(String fileName){
        File file = new File(CURR_IMAGE_REPO_PATH + "/" + fileName);    // 경로에 새 파일 생성
        try {
            file.delete();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/*.do", method = {RequestMethod.POST, RequestMethod.GET})
    protected ModelAndView viewForm(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String viewName = (String) request.getAttribute("viewName");
        ModelAndView mav = new ModelAndView(viewName);
        return mav;
    }

    protected String calcSearchPeriod(String fixedSearchPeriod){
        String beginDate = null;
        String endDate = null;
        String endYear = null;
        String endMonth = null;
        String endDay = null;
        String beginYear = null;
        String beginMonth = null;
        String beginDay = null;
        DecimalFormat df = new DecimalFormat("00");
        Calendar cal = Calendar.getInstance();

        endYear = Integer.toString(cal.get(Calendar.YEAR));
        endMonth = df.format(cal.get(Calendar.MONTH) + 1);
        endDay = df.format(cal.get(Calendar.DATE));
        endDate = endYear + "-" + endMonth + "-" + endDay;  // 날짜 폼

        if (fixedSearchPeriod == null){
            cal.add(cal.MONTH, -4);
        }else if (fixedSearchPeriod.equals("one_week")){
            cal.add(Calendar.DAY_OF_YEAR, -7);
        }else if (fixedSearchPeriod.equals("two_week")){
            cal.add(Calendar.DAY_OF_YEAR, -14);
        }else if (fixedSearchPeriod.equals("one_month")){
            cal.add(cal.MONTH, -1);
        }else if (fixedSearchPeriod.equals("two_month")){
            cal.add(cal.MONTH, -2);
        }else if (fixedSearchPeriod.equals("three_month")){
            cal.add(cal.MONTH, -3);
        }else if (fixedSearchPeriod.equals("four_month")){
            cal.add(cal.MONTH, -4);
        }

        beginYear = Integer.toString(cal.get(Calendar.YEAR));
        beginMonth = df.format(cal.get(Calendar.MONTH) + 1);
        beginDay = df.format(cal.get(Calendar.DATE));
        beginDate = beginYear + "-" + beginMonth + "-" + beginDay;

        return beginDate + "," + endDate;
    }
}

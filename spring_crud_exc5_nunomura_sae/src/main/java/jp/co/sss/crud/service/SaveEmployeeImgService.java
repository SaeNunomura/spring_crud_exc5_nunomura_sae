package jp.co.sss.crud.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;




@Service
public class SaveEmployeeImgService {

	public void execute(Integer empId, HttpSession session) throws IOException {
		
		byte[] imgBytes = (byte[]) session.getAttribute("empImgBytes");
		if (imgBytes == null || imgBytes.length == 0) {
            return;
        }
		String fileName = "emp_img" + empId + ".JPG";
		Path path = Paths.get("src/main/resources/static/images",fileName);
		Files.write(path, imgBytes);
		session.removeAttribute("empImgBytes");
        session.removeAttribute("empImgName");

	}
}

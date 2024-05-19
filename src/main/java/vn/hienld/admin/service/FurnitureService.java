package vn.hienld.admin.service;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import vn.hienld.admin.dto.FurnitureDTO;
import vn.hienld.admin.model.Furniture;

import java.util.List;
import java.util.Map;

public interface FurnitureService {
    Page<FurnitureDTO> findAll(FurnitureDTO dto);
    void delete(FurnitureDTO dto);
    Furniture save(FurnitureDTO furnitureDTO);
    List<String> addFile(String nameFurniture,  List<MultipartFile> fileUpload);
    void deleteFile(Integer id, String nameFile);
    Map<String, String> getFile(Integer id);
}

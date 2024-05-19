package vn.hienld.admin.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.hienld.admin.dto.FurnitureDTO;
import vn.hienld.admin.error.BadRequestException;
import vn.hienld.admin.model.Furniture;
import vn.hienld.admin.repository.FurnitureRepository;
import vn.hienld.admin.repository.RoomRepository;
import vn.hienld.admin.service.FurnitureService;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Service
@Slf4j
public class FurnitureServiceImpl implements FurnitureService {

    @Autowired
    FurnitureRepository furnitureRepository;
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public Page<FurnitureDTO> findAll(FurnitureDTO dto) {
        Pageable pageable = PageRequest.of(dto.getPage() - 1, dto.getSize());
        return furnitureRepository.findAll(dto.getName(), dto.getRoomId(), dto.getStatus(), pageable)
                .map(p -> objectMapper.convertValue(p, FurnitureDTO.class));
    }

    @Override
    public Furniture save(FurnitureDTO furnitureDTO) {
        if(furnitureDTO.getId() == null){
            return add(furnitureDTO);
        }
        return update(furnitureDTO);
    }

    @Override
    public List<String> addFile(String nameFurniture, List<MultipartFile> fileUpload) {
        List<String> nameFiles = new ArrayList<>();
        LocalDateTime myObj = LocalDateTime.now();
        try {
            ClassPathResource resource = new ClassPathResource(".");
            String absolutePath = resource.getFile().getAbsolutePath();
            Path imagesPath = Paths.get(absolutePath, "images");
            if (!Files.exists(imagesPath)) {
                Files.createDirectories(imagesPath);
            }
            int index = 0;
            for (MultipartFile file : fileUpload) {
                InputStream inputStream = file.getInputStream();
                String ext = FilenameUtils.getExtension(file.getOriginalFilename());
                DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyyHHmmss");

                String formattedDate = myObj.format(myFormatObj);
                String nameFile = formattedDate + "_" + nameFurniture + "_" + index++ + "." + ext;
                Files.copy(inputStream,imagesPath.resolve(nameFile),
                        StandardCopyOption.REPLACE_EXISTING);
                nameFiles.add(nameFile);
            }
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }

        return nameFiles;
    }

    @Override
    public void deleteFile(Integer id, String nameFile) {
        try {
            ClassPathResource resource = new ClassPathResource(".");
            String absolutePath = resource.getFile().getAbsolutePath();
            Path imagesPath = Paths.get(absolutePath, "images");
            File file = new File(imagesPath + "/" + nameFile);
            file.delete();
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public Map<String, String> getFile(Integer id) {
        Furniture furniture = furnitureRepository.getReferenceById(id);
        Map<String, String> data = new HashMap<>();
        try {
            ClassPathResource resource = new ClassPathResource(".");
            String absolutePath = resource.getFile().getAbsolutePath();
            Path imagesPath = Paths.get(absolutePath, "images");
            List<String> images = List.of(furniture.getImage().split(","));
            for (String image : images) {
                if (image.endsWith(".png")) {
                    File file = new File(imagesPath + "/" + image);
                    byte[] fileContent = Files.readAllBytes(file.toPath());
                    String base64 = Base64.getEncoder().encodeToString(fileContent);
                    data.put(image, "data:image/png;base64," + base64);
                }
                if (image.endsWith(".jpg")) {
                    File file = new File(imagesPath + "/" + image);
                    byte[] fileContent = Files.readAllBytes(file.toPath());
                    String base64 = Base64.getEncoder().encodeToString(fileContent);
                    data.put(image, "data:image/jpg;base64," + base64);
                }
                if (image.endsWith(".gif")) {
                    File file = new File(imagesPath + "/" + image);
                    byte[] fileContent = Files.readAllBytes(file.toPath());
                    String base64 = Base64.getEncoder().encodeToString(fileContent);
                    data.put(image, "data:image/gif;base64," + base64);
                }
                if (image.endsWith(".bmp")) {
                    File file = new File(imagesPath + "/" + image);
                    byte[] fileContent = Files.readAllBytes(file.toPath());
                    String base64 = Base64.getEncoder().encodeToString(fileContent);
                    data.put(image, "data:image/bmp;base64," + base64);
                }
            }

        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }

        return data;
    }

    public Furniture add(FurnitureDTO dto) {
        Furniture furniture = new Furniture();
        return getFurniture(dto, furniture);
    }

    private Furniture getFurniture(FurnitureDTO dto, Furniture furniture) {
        furniture.setName(dto.getName());
        furniture.setPrice(dto.getPrice());
        furniture.setImage(dto.getImage());
        furniture.setStatus(dto.getStatus());
        furniture.setRoomId(dto.getRoomId());
        furniture.setRoomName(dto.getRoomName());
        furniture.setBuildingId(dto.getBuildingId());
        furniture.setBuildingName(dto.getBuildingName());
        furniture.setDescription(dto.getDescription());

        return furnitureRepository.save(furniture);
    }

    public Furniture update(FurnitureDTO dto) {
        Furniture furniture = furnitureRepository.getReferenceById(dto.getId());
        return getFurniture(dto, furniture);
    }

    @Override
    public void delete(FurnitureDTO dto) {
        try{
            furnitureRepository.delete(furnitureRepository.getReferenceById(dto.getId()));
        }catch (Exception ex){
            log.error("Delete furniture exception : {}", ex.getMessage());
            throw new BadRequestException("Có lỗi sảy ra trong quá trình thực hiện!");
        }
    }
}

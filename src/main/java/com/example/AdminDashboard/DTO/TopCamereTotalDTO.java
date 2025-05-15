package com.example.AdminDashboard.DTO;

public class TopCamereTotalDTO {
    private CameraDTO cameraDTO;
    private Double total;

    public TopCamereTotalDTO(Double total, CameraDTO cameraDTO) {
        this.total = total;
        this.cameraDTO = cameraDTO;
    }

    public TopCamereTotalDTO() {
    }

    public CameraDTO getCameraDTO() {
        return cameraDTO;
    }

    public void setCameraDTO(CameraDTO cameraDTO) {
        this.cameraDTO = cameraDTO;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}

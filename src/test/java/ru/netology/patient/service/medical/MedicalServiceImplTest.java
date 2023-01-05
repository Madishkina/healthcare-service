package ru.netology.patient.service.medical;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import ru.netology.patient.entity.BloodPressure;
import ru.netology.patient.entity.HealthInfo;
import ru.netology.patient.entity.PatientInfo;
import ru.netology.patient.repository.PatientInfoFileRepository;
import ru.netology.patient.service.alert.SendAlertService;
import ru.netology.patient.service.alert.SendAlertServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;


class MedicalServiceImplTest {

    
    @Test
    void checkBloodPressure() {
        PatientInfoFileRepository patientInfoFileRepositoryMock = Mockito.mock(PatientInfoFileRepository.class);
        Mockito.when(patientInfoFileRepositoryMock.getById((Mockito.anyString())))
                .thenReturn(new PatientInfo("2", "Иван", "Иваныч",
                        LocalDate.of(1980, 5, 3),
                        new HealthInfo(new BigDecimal(36), new BloodPressure(120, 80))));

        SendAlertService alertServiceMock = Mockito.mock(SendAlertServiceImpl.class);

        MedicalService medicalService = new MedicalServiceImpl(patientInfoFileRepositoryMock, alertServiceMock);
        medicalService.checkBloodPressure("1", new BloodPressure(140, 100));


        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(alertServiceMock).send(argumentCaptor.capture());

        Assertions.assertEquals("Warning, patient with id: 2, need help", argumentCaptor.getValue());
    }

    @Test
    void checkTemperature() {
        PatientInfoFileRepository patientInfoFileRepositoryMock = Mockito.mock(PatientInfoFileRepository.class);
        Mockito.when(patientInfoFileRepositoryMock.getById((Mockito.anyString())))
                .thenReturn(new PatientInfo("2", "Иван", "Иваныч",
                        LocalDate.of(1980, 5, 3),
                        new HealthInfo(new BigDecimal(36), new BloodPressure(120, 80))));

        SendAlertService alertServiceMock = Mockito.mock(SendAlertServiceImpl.class);

        MedicalService medicalService = new MedicalServiceImpl(patientInfoFileRepositoryMock, alertServiceMock);
        medicalService.checkTemperature("1", new BigDecimal("34.00"));

        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(alertServiceMock).send(argumentCaptor.capture());
        Assertions.assertEquals("Warning, patient with id: 2, need help", argumentCaptor.getValue());
    }

    @Test
    void checkMedicalServiceNormal() {
        PatientInfoFileRepository patientInfoFileRepositoryMock = Mockito.mock(PatientInfoFileRepository.class);
        Mockito.when(patientInfoFileRepositoryMock.getById((Mockito.anyString())))
                .thenReturn(new PatientInfo("2", "Иван", "Иваныч",
                        LocalDate.of(1980, 5, 3),
                        new HealthInfo(new BigDecimal(36), new BloodPressure(120, 80))));

        SendAlertService alertServiceMock = Mockito.mock(SendAlertServiceImpl.class);
        MedicalService medicalService = new MedicalServiceImpl(patientInfoFileRepositoryMock, alertServiceMock);

        medicalService.checkTemperature("2", new BigDecimal("36.00"));
        medicalService.checkBloodPressure("2", new BloodPressure(120, 80));

        Mockito.verify(alertServiceMock, Mockito.times(0))
                .send(Mockito.anyString());

    }
}
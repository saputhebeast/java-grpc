package com.example.java_grpc;

import com.example.grpc.CreateEmployeeRequest;
import com.example.grpc.Employee;
import com.example.grpc.EmployeeListResponse;
import com.example.grpc.EmployeeRequest;
import com.example.grpc.EmployeeResponse;
import com.example.grpc.EmployeeServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@GrpcService
@AllArgsConstructor
public class EmployeeServiceImpl extends EmployeeServiceGrpc.EmployeeServiceImplBase {

    @NonNull
    private final EmployeeRepository employeeRepository;

    private final List<EmployeeEntity> employeeList = new ArrayList<>();

    @Override
    public void createEmployee(CreateEmployeeRequest request, StreamObserver<EmployeeResponse> responseObserver) {
        EmployeeEntity employeeEntity = new EmployeeEntity();
        employeeEntity.setName(request.getName());
        employeeEntity.setPosition(request.getPosition());


        EmployeeEntity savedEmployee = employeeRepository.save(employeeEntity);

        Employee employee = Employee.newBuilder()
                .setId(savedEmployee.getId())
                .setName(savedEmployee.getName())
                .setPosition(savedEmployee.getPosition())
                .build();

        EmployeeResponse response = EmployeeResponse.newBuilder().setEmployee(employee).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getEmployee(EmployeeRequest request, StreamObserver<EmployeeResponse> responseObserver) {
        EmployeeEntity employeeEntity = employeeRepository.findById(request.getId()).orElse(null);

        Employee employee;
        if (employeeEntity != null) {
            employee = Employee.newBuilder()
                    .setId(employeeEntity.getId())
                    .setName(employeeEntity.getName())
                    .setPosition(employeeEntity.getPosition())
                    .build();
        } else {
            employee = Employee.getDefaultInstance();
        }

        EmployeeResponse response = EmployeeResponse.newBuilder().setEmployee(employee).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getAllEmployees(com.google.protobuf.Empty request, StreamObserver<EmployeeListResponse> responseObserver) {
        List<EmployeeEntity> employeeEntities = employeeRepository.findAll();

        List<Employee> employees = employeeEntities.stream()
                .map(employeeEntity -> Employee.newBuilder()
                        .setId(employeeEntity.getId())
                        .setName(employeeEntity.getName())
                        .setPosition(employeeEntity.getPosition())
                        .build())
                .collect(Collectors.toList());

        EmployeeListResponse response = EmployeeListResponse.newBuilder()
                .addAllEmployees(employees)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}

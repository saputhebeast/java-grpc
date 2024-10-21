# To create an employee
```
grpcurl -plaintext -d '{"name": "John Doe", "position": "Developer"}' localhost:9090 com.example.grpc.EmployeeService/CreateEmployee
```

# To get an employee by id
```
grpcurl -plaintext -d '{"id": 1}' localhost:9090 com.example.grpc.EmployeeService/GetEmployee
```

# To get all employees
```
grpcurl -plaintext localhost:9090 com.example.grpc.EmployeeService/GetAllEmployees
```

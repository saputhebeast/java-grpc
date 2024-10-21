# To create an employee
```json
grpcurl -plaintext -d '{"name": "John Doe", "position": "Developer"}' localhost:9090 com.example.grpc.EmployeeService/CreateEmployee
```

# To get an employee by id
```json
grpcurl -plaintext -d '{"id": 1}' localhost:9090 com.example.grpc.EmployeeService/GetEmployee
```

# To get all employees
```json
grpcurl -plaintext localhost:9090 com.example.grpc.EmployeeService/GetAllEmployees
```

cmake_minimum_required(VERSION 3.10)
project(CppVulnWeb)

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

# 查找并链接Crow框架
find_package(Crow CONFIG QUIET)
if(NOT Crow_FOUND)
    message(STATUS "Crow not found, using FetchContent")
    include(FetchContent)
    FetchContent_Declare(
        Crow
        GIT_REPOSITORY https://github.com/CrowCpp/Crow.git
        GIT_TAG master
    )
    FetchContent_MakeAvailable(Crow)
endif()

# 查找libcurl用于SSRF测试
find_package(CURL REQUIRED)

# 包含目录
include_directories(${CMAKE_SOURCE_DIR})
include_directories(${CMAKE_SOURCE_DIR}/common)
include_directories(${CMAKE_SOURCE_DIR}/interface)

# 添加所有源文件
set(SOURCES
    main.cpp
    func_call.cpp
    common/vuln_parent.cpp
    common/vuln_child.cpp
    interface/vuln_interface.cpp
    cmd_exec/sub_class_test.cpp
    cmd_exec/interface_impl.cpp
    code_exec/sub_class_test.cpp
    code_exec/interface_impl.cpp
    ssrf/sub_class_test.cpp
    ssrf/interface_impl.cpp
)

# 创建可执行文件
add_executable(${PROJECT_NAME} ${SOURCES})

# 链接库
target_link_libraries(${PROJECT_NAME} PRIVATE Crow::Crow)
target_link_libraries(${PROJECT_NAME} PRIVATE CURL::libcurl)

# 复制静态文件到构建目录
file(COPY ${CMAKE_SOURCE_DIR}/static DESTINATION ${CMAKE_BINARY_DIR})
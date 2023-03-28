# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

cmake_minimum_required(VERSION 3.22.1)

project("opengles")

add_subdirectory(glm)
aux_source_directory(. src_list)
aux_source_directory(./window window_list)
aux_source_directory(./utils utils_list)

add_library(
        opengles
        SHARED
        native-lib.cpp
        ${src_list}
        ${window_list}
        ${utils_list}
)


find_library(log-lib log)
find_library(EGL-lib EGL)
find_library(GLESv2-lib GLESv2)
find_library(dl-lib dl)
find_library(android-lib android)

target_link_libraries(
        opengles
        glm
        ${android-lib}
        ${EGL-lib}
        ${dl-lib}
        ${GLESv2-lib}
        ${log-lib}
)
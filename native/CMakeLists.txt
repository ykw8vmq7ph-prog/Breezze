cmake_minimum_required(VERSION 3.16)
project(breezze_analysis LANGUAGES CXX)

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED ON)
set(CMAKE_CXX_EXTENSIONS OFF)

# Set default build type to Release if not specified
if(NOT CMAKE_BUILD_TYPE)
    set(CMAKE_BUILD_TYPE Release)
endif()

# Platform-specific settings
if(WIN32)
    # Windows-specific settings
    add_compile_options(/MP /EHsc /DNOMINMAX)
    if(CMAKE_CXX_COMPILER_ID STREQUAL "GNU")
        add_compile_options(-static-libgcc -static-libstdc++)
    endif()
elseif(APPLE)
    # macOS-specific settings
    add_compile_options(-fPIC)
    set(CMAKE_MACOSX_RPATH ON)
else()
    # Linux/Unix settings
    add_compile_options(-fPIC -pthread)
endif()

# Include directories
include_directories(${CMAKE_CURRENT_SOURCE_DIR}/analyzer)

# Source files
set(ANALYZER_SOURCES
    analyzer/audio_analysis.cpp
    analyzer/mel_spectrogram.cpp
    analyzer/resampler.cpp
    analyzer/tempo_analysis.cpp
    analyzer/vocal_spectrogram.cpp
)

# Create shared library
add_library(breezze_analysis SHARED ${ANALYZER_SOURCES})

# Set output name
set_target_properties(breezze_analysis PROPERTIES
    OUTPUT_NAME "breezze_analysis"
    VERSION ${PROJECT_VERSION}
    SOVERSION ${PROJECT_VERSION_MAJOR}
)

# Install targets
install(TARGETS breezze_analysis
    LIBRARY DESTINATION lib
    ARCHIVE DESTINATION lib
    RUNTIME DESTINATION bin
)

# Install headers
install(FILES
    analyzer/audio_analysis.h
    analyzer/mel_spectrogram.h
    analyzer/resampler.h
    analyzer/tempo_analysis.h
    analyzer/vocal_spectrogram.h
    DESTINATION include/breezze_analysis
)
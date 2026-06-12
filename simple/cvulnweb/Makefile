# Makefile for cvulnweb - C Web vulnerability test code using libmicrohttpd
# Build configuration for security analysis tool verification

CC = gcc
CFLAGS = -Wall -Wextra -g -I. -Icommon -Iinterface
LDFLAGS = -lmicrohttpd -lcurl -ldl

# Source files
SRCS = main.c func_call.c common/vuln_parent.c common/vuln_child.c interface/vuln_interface.c \
       cmd_exec/sub_struct_test.c cmd_exec/interface_impl.c \
       code_exec/sub_struct_test.c code_exec/interface_impl.c \
       ssrf/sub_struct_test.c ssrf/interface_impl.c

# Object files
OBJS = $(SRCS:.c=.o)

# Target executable
TARGET = cvulnweb

# Default target
all: $(TARGET)

# Link object files
$(TARGET): $(OBJS)
	$(CC) $(OBJS) -o $(TARGET) $(LDFLAGS)

# Compile rules
%.o: %.c
	$(CC) $(CFLAGS) -c $< -o $@

# Clean build artifacts
clean:
	rm -f $(OBJS) $(TARGET)

# Dependency rules
main.o: main.c func_call.h common/vuln_parent.h common/vuln_child.h interface/vuln_interface.h
func_call.o: func_call.c func_call.h
common/vuln_parent.o: common/vuln_parent.c common/vuln_parent.h
common/vuln_child.o: common/vuln_child.c common/vuln_child.h common/vuln_parent.h
interface/vuln_interface.o: interface/vuln_interface.c interface/vuln_interface.h
cmd_exec/sub_struct_test.o: cmd_exec/sub_struct_test.c common/vuln_parent.h common/vuln_child.h
cmd_exec/interface_impl.o: cmd_exec/interface_impl.c interface/vuln_interface.h common/vuln_parent.h
code_exec/sub_struct_test.o: code_exec/sub_struct_test.c common/vuln_parent.h common/vuln_child.h
code_exec/interface_impl.o: code_exec/interface_impl.c interface/vuln_interface.h common/vuln_parent.h
ssrf/sub_struct_test.o: ssrf/sub_struct_test.c common/vuln_parent.h common/vuln_child.h
ssrf/interface_impl.o: ssrf/interface_impl.c interface/vuln_interface.h common/vuln_parent.h

.PHONY: all clean
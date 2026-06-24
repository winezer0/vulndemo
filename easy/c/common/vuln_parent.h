#ifndef VULN_PARENT_H
#define VULN_PARENT_H

typedef struct {
    void (*exec)(const char*);
} VulnParent;

void parent_exec(const char* cmd);

#endif

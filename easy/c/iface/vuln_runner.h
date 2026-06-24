#ifndef VULN_RUNNER_H
#define VULN_RUNNER_H

typedef struct {
    void (*run)(void*, const char*);
} VulnRunner;

#endif

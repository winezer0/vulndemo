const express = require('express');
const { execSync } = require('child_process');
const axios = require('axios');
const path = require('path');

const { crossFileCmdExec, crossFileCmdExecHardcoded, crossFileCodeExec, crossFileCodeExecHardcoded, crossFileSSRF, crossFileSSRFHardcoded } = require('./funcCall');
const { subclassCmdExec, subclassCmdExecHardcoded } = require('./cmd_exec/SubClassTest');
const CmdExecInterfaceImpl = require('./cmd_exec/InterfaceImpl');
const { subclassCodeExec, subclassCodeExecHardcoded } = require('./code_exec/SubClassTest');
const CodeExecInterfaceImpl = require('./code_exec/InterfaceImpl');
const { subclassSSRF, subclassSSRFHardcoded } = require('./ssrf/SubClassTest');
const SSRFInterfaceImpl = require('./ssrf/InterfaceImpl');
const VulnChild = require('./common/VulnChild');

const app = express();
const PORT = 3000;
app.use(express.static(path.join(__dirname, 'public')));
app.use(express.json());

const vulnChild = new VulnChild();
const cmdImpl = new CmdExecInterfaceImpl();
const codeImpl = new CodeExecInterfaceImpl();
const ssrfImpl = new SSRFInterfaceImpl();

// ===================== 命令执行漏洞路由 =====================

// 1. 外部参数 - 直接命令执行
app.get('/cmd/direct', (req, res) => {
  // 漏洞点: 命令执行 - 用户输入直接传入execSync
  const input = req.query.input || 'default';
  const result = execSync(`echo ${input}`, { encoding: 'utf-8' });
  res.json({ method: 'direct', result });
});

// 2. 硬编码 - 直接命令执行
app.get('/cmd/direct-hardcoded', (req, res) => {
  // 漏洞点: 命令执行 - 硬编码参数执行
  const result = execSync('echo hardcoded_direct_test', { encoding: 'utf-8' });
  res.json({ method: 'direct-hardcoded', result });
});

// 3. 外部参数 - 跨文件函数调用
app.get('/cmd/crossfile', (req, res) => {
  const input = req.query.input || 'default';
  const result = crossFileCmdExec(input);
  res.json({ method: 'crossfile', result });
});

// 4. 硬编码 - 跨文件函数调用
app.get('/cmd/crossfile-hardcoded', (req, res) => {
  const result = crossFileCmdExecHardcoded();
  res.json({ method: 'crossfile-hardcoded', result });
});

// 5. 外部参数 - 子类继承调用
app.get('/cmd/subclass', (req, res) => {
  const input = req.query.input || 'default';
  const result = subclassCmdExec(input);
  res.json({ method: 'subclass', result });
});

// 6. 硬编码 - 子类继承调用
app.get('/cmd/subclass-hardcoded', (req, res) => {
  const result = subclassCmdExecHardcoded();
  res.json({ method: 'subclass-hardcoded', result });
});

// 7. 外部参数 - 接口实现类调用
app.get('/cmd/interface', (req, res) => {
  const input = req.query.input || 'default';
  const result = cmdImpl.doExecCmd(input);
  res.json({ method: 'interface', result });
});

// 8. 硬编码 - 接口实现类调用
app.get('/cmd/interface-hardcoded', (req, res) => {
  const result = cmdImpl.doExecCmdHardcoded();
  res.json({ method: 'interface-hardcoded', result });
});

// ===================== 代码执行漏洞路由 =====================

// 9. 外部参数 - 直接代码执行
app.get('/code/direct', (req, res) => {
  // 漏洞点: 代码执行 - 用户输入直接传入eval
  const input = req.query.input || '"default"';
  const result = eval(input);
  res.json({ method: 'direct', result });
});

// 10. 硬编码 - 直接代码执行
app.get('/code/direct-hardcoded', (req, res) => {
  // 漏洞点: 代码执行 - 硬编码字符串eval
  const result = eval('"hardcoded_direct_eval" + 1');
  res.json({ method: 'direct-hardcoded', result });
});

// 11. 外部参数 - 跨文件函数调用
app.get('/code/crossfile', (req, res) => {
  const input = req.query.input || '"default"';
  const result = crossFileCodeExec(input);
  res.json({ method: 'crossfile', result });
});

// 12. 硬编码 - 跨文件函数调用
app.get('/code/crossfile-hardcoded', (req, res) => {
  const result = crossFileCodeExecHardcoded();
  res.json({ method: 'crossfile-hardcoded', result });
});

// 13. 外部参数 - 子类继承调用
app.get('/code/subclass', (req, res) => {
  const input = req.query.input || '"default"';
  const result = subclassCodeExec(input);
  res.json({ method: 'subclass', result });
});

// 14. 硬编码 - 子类继承调用
app.get('/code/subclass-hardcoded', (req, res) => {
  const result = subclassCodeExecHardcoded();
  res.json({ method: 'subclass-hardcoded', result });
});

// 15. 外部参数 - 接口实现类调用
app.get('/code/interface', (req, res) => {
  const input = req.query.input || '"default"';
  const result = codeImpl.doExecCode(input);
  res.json({ method: 'interface', result });
});

// 16. 硬编码 - 接口实现类调用
app.get('/code/interface-hardcoded', (req, res) => {
  const result = codeImpl.doExecCodeHardcoded();
  res.json({ method: 'interface-hardcoded', result });
});

// ===================== SSRF漏洞路由 =====================

// 17. 外部参数 - 直接SSRF
app.get('/ssrf/direct', async (req, res) => {
  // 漏洞点: SSRF - 用户URL直接发起请求
  const url = req.query.url || 'http://127.0.0.1:8080';
  try {
    const response = await axios.get(url);
    res.json({ method: 'direct', result: response.data });
  } catch (e) {
    res.json({ method: 'direct', error: e.message });
  }
});

// 18. 硬编码 - 直接SSRF
app.get('/ssrf/direct-hardcoded', async (req, res) => {
  // 漏洞点: SSRF - 硬编码URL请求
  try {
    const response = await axios.get('http://127.0.0.1:8080/internal');
    res.json({ method: 'direct-hardcoded', result: response.data });
  } catch (e) {
    res.json({ method: 'direct-hardcoded', error: e.message });
  }
});

// 19. 外部参数 - 跨文件函数调用
app.get('/ssrf/crossfile', async (req, res) => {
  const url = req.query.url || 'http://127.0.0.1:8080';
  try {
    const result = await crossFileSSRF(url);
    res.json({ method: 'crossfile', result });
  } catch (e) {
    res.json({ method: 'crossfile', error: e.message });
  }
});

// 20. 硬编码 - 跨文件函数调用
app.get('/ssrf/crossfile-hardcoded', async (req, res) => {
  try {
    const result = await crossFileSSRFHardcoded();
    res.json({ method: 'crossfile-hardcoded', result });
  } catch (e) {
    res.json({ method: 'crossfile-hardcoded', error: e.message });
  }
});

// 21. 外部参数 - 子类继承调用
app.get('/ssrf/subclass', async (req, res) => {
  const url = req.query.url || 'http://127.0.0.1:8080';
  try {
    const result = await subclassSSRF(url);
    res.json({ method: 'subclass', result });
  } catch (e) {
    res.json({ method: 'subclass', error: e.message });
  }
});

// 22. 硬编码 - 子类继承调用
app.get('/ssrf/subclass-hardcoded', async (req, res) => {
  try {
    const result = await subclassSSRFHardcoded();
    res.json({ method: 'subclass-hardcoded', result });
  } catch (e) {
    res.json({ method: 'subclass-hardcoded', error: e.message });
  }
});

// 23. 外部参数 - 接口实现类调用
app.get('/ssrf/interface', async (req, res) => {
  const url = req.query.url || 'http://127.0.0.1:8080';
  try {
    const result = await ssrfImpl.doFetchSSRF(url);
    res.json({ method: 'interface', result });
  } catch (e) {
    res.json({ method: 'interface', error: e.message });
  }
});

// 24. 硬编码 - 接口实现类调用
app.get('/ssrf/interface-hardcoded', async (req, res) => {
  try {
    const result = await ssrfImpl.doFetchSSRFHardcoded();
    res.json({ method: 'interface-hardcoded', result });
  } catch (e) {
    res.json({ method: 'interface-hardcoded', error: e.message });
  }
});

app.listen(PORT, () => {
  console.log(`jsvulnweb server running at http://localhost:${PORT}`);
});

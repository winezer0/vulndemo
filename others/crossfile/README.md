# Cross-language cross-file call-chain fixture

The Go part is indexed as a standalone module: `example.com/crossfile`. The
Java part is rooted at `java/src/main/java` and uses the package
`example.crossfile`.

It covers:

- cross-package function calls: `handlers.Handle -> services.Execute`;
- receiver method calls: `handlers.Handle -> services.Service.Execute -> services.Service.run`;
- `returns_to` and argument flow: `input.FromRequest -> handlers.value -> services` arguments;
- interface dispatch: `RunWithInterface -> Runner.Run -> ShellRunner.Run`;
- external command APIs: `os/exec.Command`;
- external database APIs: `database/sql.Open` and `database/sql.DB.QueryContext`.

The Java examples cover:

- `Runtime.getRuntime().exec(command)`;
- `Runtime runtime = Runtime.getRuntime(); runtime.exec(command)`;
- `new ProcessBuilder(...).start()`;
- cross-file service calls from `CommandController`;
- interface declaration and implementation through `CommandExecutor`.

The current resolver does not yet materialize interface dispatch edges for
the Go or Java examples; the tests report these as explicit resolver gaps.

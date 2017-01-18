const gulp = require('gulp');
const mjml = require('gulp-mjml');

const srcMjml = './src/main/webapp/WEB-INF/mjml/**/*.mjml';
const dstMjml = './src/main/webapp/WEB-INF/templates';

gulp.task('mjml', function () {
    return gulp.src([srcMjml])
        .pipe(mjml())
        .pipe(gulp.dest(dstMjml))
});

gulp.task("watch", function() {
    var targets = [srcMjml];
    gulp.watch(targets, ['mjml']);
});

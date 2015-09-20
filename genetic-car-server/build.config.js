/**
 * This file contains all configuration for the build process.
 */
module.exports = {
    app_src_path: "src/main/webapp",
    package_path: "src/main/webapp/build",
    tmp_dir: '.tmp',

    app_files: {
        js: [
            'src/main/webapp/scripts/app.js',
            'src/main/webapp/scripts/app/**/*.js',
            'src/main/webapp/scripts/common/**/*.js',
            '!src/main/webapp/scripts/**/*_test.js'
        ],
        jsunit: [
            'src/main/webapp/scripts/app/**/*_test.js',
            'src/main/webapp/scripts/common/**/*_test.js'],

        tpl: [
            'src/main/webapp/scripts/app/**/*-tpl.html',
            'src/main/webapp/scripts/common/**/*-tpl.html'
        ],

        index: ['src/main/webapp/index.html'],
        less: [
            'src/main/webapp/styles-less/**/*.less',
            'src/main/webapp/scripts/common/**/*.less',
            'src/main/webapp/scripts/app/**/*.less'
        ]
    },

    /**
     * This is a collection of files used during testing only.
     */
    test_files: {
        js: [
            'bower_components/angular-mocks/angular-mocks.js'
        ]
    },

    vendor_files: {
        js: [
            'bower_components/angular/angular.js',
            'bower_components/angular-resource/angular-resource.js',
            'bower_components/angular-sanitize/angular-sanitize.js',
            'bower_components/angular-ui-router/release/angular-ui-router.js',
            'bower_components/lodash/lodash.js'
        ],
        css: [],
        assets: []
    }
};
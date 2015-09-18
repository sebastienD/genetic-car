'use strict';

module.exports = function (grunt) {

    // Load grunt tasks automatically
    require('load-grunt-tasks')(grunt);

    // Time how long tasks take. Can help when optimizing build times
    require('time-grunt')(grunt);

    var buildConfig = require('./build.config.js');

    var taskConfig = {
        build_dir: 'www',
        compile_dir: 'dist',

        pkg: grunt.file.readJSON('package.json'), // ????

        // Watches files for changes and runs tasks based on the changed files
        chokidar: {
            options: {
                livereload: '<%= connect.options.livereload %>'
            },

            bower: {
                files: ['bower.json'],
                tasks: ['wiredep']
            },
            js: {
                files: ['<%= app_files.js %>'],
                tasks: [/*'karmaconfig', 'karma',*/ 'index:dev', 'newer:jshint:all']
            },
            index: {
                files: ['<%= app_files.index %>'],
                tasks: ['index:dev']
            },
            jsTest: {
                files: ['<%= app_files.jsunit %>'],
                tasks: [/*'karmaconfig', 'karma',*/ 'index:dev', 'newer:jshint:test']
            },
            styles: {
                files: ['<%= app_src_path %>/styles/{,*/}*.css'],
                tasks: ['newer:copy:styles', 'autoprefixer']
            },
            app_assets: {
                files: [
                    '<%= app_src_path %>/scripts/app/{,**/}*.{png,jpg,jpeg,gif,webp,svg}',
                    '<%= app_src_path %>/scripts/common/{,**/}*.{png,jpg,jpeg,gif,webp,svg}'
                ],
                tasks: ['copy:app_assets']
            },
            less: {
                files: ['<%= app_files.less %>'],
                tasks: ['less']
            },
            gruntfile: {
                files: ['Gruntfile.js']
            },
            templates: {
                files: ['<%= app_files.tpl %>']
            },
            assets: {
                files: '<%= app_src_path %>/images/{,*/}*.{png,jpg,jpeg,gif,webp,svg}'

            }
        },

        // The actual grunt server settings
        connect: {
            options: {
                port: 9000,
                // Change this to '0.0.0.0' to access the server from outside.
                hostname: 'localhost',
                livereload: 35729
            },
            dev: {
                options: {
                    open: true,
                    middleware: function (connect) {
                        return [
                            connect.static(grunt.config('tmp_dir')),
                            connect().use(
                                '/bower_components',
                                connect.static('./bower_components')
                            ),
                            connect.static(grunt.config('app_src_path'))
                        ];
                    }
                }
            },
            test: {
                options: {
                    port: 9004,
                    middleware: function (connect) {
                        return [
                            connect.static(grunt.config('tmp_dir')),
                            connect.static('test'),
                            connect().use(
                                '/bower_components',
                                connect.static('./bower_components')
                            ),
                            connect.static(grunt.config('app_src_path'))
                        ];
                    }
                }
            },
            dist: {
                options: {
                    open: true,
                    middleware: function (connect) {
                        return [
                            connect.static(grunt.config('package_path'))
                        ];
                    }
                }
            }
        },

        // Make sure code styles are up to par and there are no obvious mistakes
        jshint: {
            options: {
                jshintrc: '.jshintrc',
                reporter: require('jshint-stylish'),
                force: true
            },
            all: {
                src: [
                    'Gruntfile.js',
                    '<%= app_files.js %>'
                ]
            },
            test: {
                options: {
                    jshintrc: '<%= app_src_path %>/test/.jshintrc'
                },
                src: ['<%= app_files.jsunit %>']
            }
        },

        // Empties folders to start fresh
        clean: {
            dist: {
                files: [{
                    dot: true,
                    src: [
                        '<%= tmp_dir %>',
                        '<%= package_path %>/{,*/}*',
                        '!<%= package_path %>/.git*'
                    ]
                }]
            },
            server: '<%= tmp_dir %>'
        },

        // Add vendor prefixed styles
        autoprefixer: {
            options: {
                browsers: ['last 1 version']
            },
            dist: {
                files: [{
                    expand: true,
                    cwd: '<%= tmp_dir %>/styles/',
                    src: '{,*/}*.css',
                    dest: '<%= tmp_dir %>/styles/'
                }]
            }
        },

        // Renames files for browser caching purposes
        filerev: {
            dist: {
                src: [
                    '<%= tmp_dir %>/scripts/{,*/}*.js',
                    '<%= tmp_dir %>/styles/{,*/}*.css',
                    '<%= tmp_dir %>/images/{,*/}*.{png,jpg,jpeg,gif,webp,svg}'
                ]
            },
            dist_template: {
                src: [
                    '<%= tmp_dir %>/scripts/3-gen.templates.js',
                ]
            }
        },

        // Performs rewrites based on filerev
        usemin: {
            html: ['<%= tmp_dir %>/**/*.html'],
            css: ['<%= tmp_dir %>/styles/{,*/}*.css'],
            js: ['<%= tmp_dir %>/scripts/*.js'],
            options: {
                assetsDirs: ['<%= tmp_dir %>', '<%= tmp_dir %>/images'],
                patterns: {
                    js: [
                        [/(images\/konami\/.*?\.(?:gif|png))/gm,
                            'Update the JS to reference our revved images']
                    ]
                }
            }
        },

        // The following *-min tasks will produce minified files in the dist folder
        // By default, your `index.html`'s <!-- Usemin block --> will take care of
        // minification. These next options are pre-configured if you do not wish
        // to use the Usemin blocks.
        cssmin: {
            dist: {
                files: [
                    {
                        expand: true,     // Enable dynamic expansion.
                        cwd: '<%= tmp_dir %>',      // Src matches are relative to this path.
                        src: ['styles/*.css'], // Actual pattern(s) to match.
                        dest: '<%= tmp_dir %>/',   // Destination path prefix.
                        //ext: '.min.css',   // Dest filepaths will have this extension.
                        extDot: 'last'   // Extensions in filenames begin after the first dot
                    }
                ]
            }
        },
        uglify: {
            dist: {
                files: [
                    {
                        expand: true,     // Enable dynamic expansion.
                        cwd: '<%= tmp_dir %>',      // Src matches are relative to this path.
                        src: ['scripts/**/*.js'], // Actual pattern(s) to match.
                        dest: '<%= tmp_dir %>/',   // Destination path prefix.
                        //ext: '.min.js',   // Dest filepaths will have this extension.
                        extDot: 'last'   // Extensions in filenames begin after the first dot
                    }
                ]
            }
        },

        imagemin: {
            dist: {
                files: [{
                    expand: true,
                    cwd: '<%= tmp_dir %>/images',
                    src: '{,*/}*.{png,jpg,jpeg,gif}',
                    dest: '<%= tmp_dir %>/images'
                }]
            }
        },

        svgmin: {
            dist: {
                files: [{
                    expand: true,
                    cwd: '<%= tmp_dir %>/images',
                    src: '{,*/}*.svg',
                    dest: '<%= tmp_dir %>/images'
                }]
            }
        },

        // Copies remaining files to places other tasks can use
        copy: {
            prepare_dist: {
                expand: true,
                cwd: '<%= app_src_path %>',
                dest: '<%= tmp_dir %>',
                src: [
                    'scripts/**/*-tpl.html',
                    'images/**/*',
                    'fonts/**/*'
                ]
            },
            app_assets: {
                files: [{
                    expand: true,
                    cwd: '<%= app_src_path %>/scripts',
                    dest: '<%= tmp_dir %>/images/',
                    src: 'app/{,**/}*.{png,jpg,jpeg,gif,webp,svg}'
                }, {
                    expand: true,
                    cwd: '<%= app_src_path %>/scripts',
                    dest: '<%= tmp_dir %>/images/',
                    src: 'common/{,**/}*.{png,jpg,jpeg,gif,webp,svg}'
                }]
            },
            vendor_assets: {
                files: [{
                    expand: true,
                    cwd: 'bower_components/bootstrap-css-only',
                    src: 'fonts/*',
                    dest: '<%= tmp_dir %>'
                }]
            },
            dist: {
                files: [{
                    expand: true,
                    dot: true,
                    cwd: '<%= app_src_path %>',
                    dest: '<%= package_path %>',
                    src: [
                        '*.{ico,png,txt}',
                        '.htaccess'
                    ]
                }, {
                    expand: true,
                    cwd: '<%= tmp_dir %>',
                    src: ['scripts/*.js', 'styles/*.css', 'index.html', 'images/**/*'],
                    dest: '<%= package_path %>'
                }]
            },
            styles: {
                expand: true,
                cwd: '<%= app_src_path %>/styles',
                dest: '<%= tmp_dir %>/styles/',
                src: '{,*/}*.css'
            }
        },

        concat: {
            vendor_css: {
                src: ['<%= vendor_files.css %>'],
                dest: '<%= tmp_dir %>' + '/styles/1-vendor.css'
            },
            app_js: {
                options: {
                    banner: '\'use strict\';\n'
                },
                src: [
                    'module.prefix',
                    '<%= app_files.js %>',
                    'module.suffix'
                ],
                dest: '<%= tmp_dir %>/scripts/2-gen.js'
            },
            vendor_js: {
                src: [
                    '<%= vendor_files.js %>'
                ],
                dest: '<%= tmp_dir %>/scripts/1-vendor.js'
            }
        },

        // Run some tasks in parallel to speed up the build process
        concurrent: {
            server: [
                'copy:styles',
                'index:dev'
            ],
            imagemin: [
                'imagemin',
                'svgmin'
            ]
        },

        html2js: {
            main: {
                options: {
                    base: '<%= tmp_dir %>',
                    rename: function (moduleName) {
                        return moduleName;
                    },
                    htmlmin: {
                        collapseWhitespace: true,
                        conservativeCollapse: true,
                        collapseBooleanAttributes: false,
                        removeCommentsFromCDATA: true,
                        removeOptionalTags: true,
                        removeComments: true,
                        preserveLineBreaks: true
                    },
                    module: 'ceo',
                    existingModule: true,
                    singleModule: true
                },
                src: ['<%= tmp_dir %>/scripts/**/*-tpl.html'],
                dest: '<%= tmp_dir %>/scripts/3-gen.templates.js'
            }
        },

        /**
         * This task compiles the karma template so that changes to its file array
         * don't have to be managed manually.
         */
        karmaconfig: {
            unit: {
                dir: '<%= tmp_dir %>',
                src: [
                    '<%= vendor_files.js %>',
                    //'<%= html2js.app.dest %>',
                    //'<%= html2js.common.dest %>',
                    '<%= test_files.js %>',
                    '<%= app_files.js %>',
                    '<%= app_files.jsunit %>'
                ]
            }
        },

        // Test settings
        karma: {
            continuous: {
                configFile: '<%= tmp_dir %>/test/karma.conf.js'
                //singleRun: true // me
            }
        },
        less: {
            dist: {
                files: {
                    '<%= tmp_dir %>/styles/2-gen.css': [
                        '<%= app_files.less %>'
                    ]
                }
            }
        },
        /**
         * The `index` task compiles the `index.html` file as a Grunt template. CSS
         * and JS files co-exist here but they get split apart later.
         */
        index: {

            /**
             * During development, we don't want to have wait for compilation,
             * concatenation, minification, etc. So to avoid these steps, we simply
             * add all script files directly to the `index.html`. The
             * `src` property contains the list of included files.
             */
            dev: {
                dir: '<%= tmp_dir %>',
                src: [
                    '<%= vendor_files.js %>',
                    '<%= app_files.js %>',
                    '<%= tmp_dir %>/styles/*.css'
                ]
            },
            dist: {
                dir: '<%= tmp_dir %>',
                src: [
                    '<%= tmp_dir %>/scripts/*.js',
                    '<%= tmp_dir %>/styles/*.css'
                ]
            }
        }

    };

    // Define the configuration for all the tasks
    grunt.initConfig(grunt.util._.extend(buildConfig, taskConfig));

    /**
     * A utility function to get all app JavaScript sources.
     */
    function filterForJS(files) {
        return files.filter(function (file) {
            return file.match(/\.js$/);
        });
    }

    /**
     * A utility function to get all app CSS sources.
     */
    function filterForCSS(files) {
        return files.filter(function (file) {
            return file.match(/\.css$/);
        });
    }

    /**
     * The index.html template includes the stylesheet and javascript sources
     * based on dynamic names calculated in this Gruntfile. This task assembles
     * the list into variables for the template to use and then runs the
     * compilation.
     */
    grunt.registerMultiTask('index', 'Process index.html template', function () {
        var dirRE = new RegExp('^(src\/main\/webapp|\.tmp|target\/grunt-build)\/', 'g');
        var jsFiles = filterForJS(this.filesSrc).map(function (file) {
            return file.replace(dirRE, '');
        });
        var cssFiles = filterForCSS(this.filesSrc).map(function (file) {
            return file.replace(dirRE, '');
        });

        grunt.file.copy('src/main/webapp/index.html', this.data.dir + '/index.html', {
            process: function (contents, path) {
                return grunt.template.process(contents, {
                    data: {
                        scripts: jsFiles,
                        styles: cssFiles,
                        version: grunt.config('pkg.version')
                    }
                });
            }
        });
    });

    /**
     * In order to avoid having to specify manually the files needed for karma to
     * run, we use grunt to manage the list for us. The `karma/*` files are
     * compiled as grunt templates for use by Karma. Yay!
     */
    grunt.registerMultiTask('karmaconfig', 'Process karma config templates', function () {
        var jsFiles = filterForJS(this.filesSrc);

        grunt.file.copy(grunt.config('app_src_path') + '/test/karma-unit.tpl.js', grunt.config('tmp_dir') + '/test/karma.conf.js', {
            process: function (contents, path) {
                return grunt.template.process(contents, {
                    data: {
                        scripts: jsFiles
                    }
                });
            }
        });
    });

    grunt.registerTask('serve', 'Compile then start a connect web server', function (target) {
        if (target === 'dist') {
            return grunt.task.run(['build', 'connect:dist:keepalive']);
        } else {
            grunt.task.run([
                'clean:server',
                //'wiredep',
                'less',
                'concat:vendor_css',
                'copy:vendor_assets',
                'copy:app_assets',
                //'karmaconfig',
                //'karma', // me
                'concurrent:server',
                'autoprefixer',
                'connect:dev',
                'chokidar'
            ]);
        }
    });

    grunt.registerTask('test', [
        'clean:server',
        //'wiredep',
        'less',
        'concat:vendor_css',
        'connect:test',
        //'karmaconfig',
        //'karma' //me
    ]);

    grunt.registerTask('build', [
        'clean:dist',
        //'wiredep',
        'less',
        'concat',
        //'karmaconfig',
        //'karma', // me
        'autoprefixer',
        'copy:vendor_assets',
        'copy:app_assets',
        'copy:prepare_dist',
        'concurrent:imagemin',
        'filerev:dist',
        'usemin',
        'html2js',
        'filerev:dist_template',
        'index:dist',
        'uglify',
        'cssmin',
        'copy:dist',
        'jshint:all'
    ]);

    grunt.registerTask('default', [
        'build'
    ]);

};

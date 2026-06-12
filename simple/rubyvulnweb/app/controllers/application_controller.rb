class ApplicationController < ActionController::Base
  # 公共控制器，提供基础方法
  def index
    render html: File.read(Rails.root.join('public', 'index.html')).html_safe
  end
end
